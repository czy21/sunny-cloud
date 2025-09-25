using System.Collections;
using System.Diagnostics;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using Sunny.Framework.Core.Util;

namespace Sunny.Framework.Web.Middleware;

public class HttpLogMiddleware
{
    private readonly ILogger<HttpLogMiddleware> _logger;
    private readonly RequestDelegate _next;

    public HttpLogMiddleware(ILogger<HttpLogMiddleware> logger, RequestDelegate next)
    {
        _logger = logger;
        _next = next;
    }

    public async Task Invoke(HttpContext context)
    {
        var traceId = Guid.NewGuid().ToString();
        var stopwatch = Stopwatch.StartNew();

        if (_logger.IsEnabled(LogLevel.Debug))
        {
            var headers = context.Request.Headers.OrderBy(t => t.Key).ToDictionary(t => t.Key, t => string.Join("; ", t.Value.ToArray()));
            var requestBody = "";

            context.Request.EnableBuffering();
            requestBody = await new StreamReader(context.Request.Body).ReadToEndAsync().ConfigureAwait(false);
            context.Request.Body.Position = 0;
            if (context.Request.Headers.TryGetValue("Content-Type", out var requestContentType) && requestContentType.ToString().Contains("application/json", StringComparison.CurrentCultureIgnoreCase))
            {
                requestBody = JsonUtil.Serialize(JsonUtil.Deserialize<dynamic>(requestBody));
            }

            var requestFormat = "[HTTP Request  {TraceId}] {Method} {Path}\nHeaders: {Headers}\nBody: {Body}";
            var requestFormatArgs = new ArrayList { traceId, context.Request.Method, context.Request.Path, JsonUtil.Serialize(headers, true), requestBody };
            _logger.LogInformation(requestFormat, requestFormatArgs.ToArray());
        }

        var originalBodyStream = context.Response.Body;
        using var responseStream = new MemoryStream();
        context.Response.Body = responseStream;

        await _next(context);

        var responseBody = "";
        if (_logger.IsEnabled(LogLevel.Debug))
        {
            context.Response.Body.Seek(0, SeekOrigin.Begin);
            responseBody = await new StreamReader(context.Response.Body).ReadToEndAsync();
            context.Response.Body.Seek(0, SeekOrigin.Begin);
        }

        await responseStream.CopyToAsync(originalBodyStream);

        stopwatch.Stop();
        var responseFormat = "[HTTP Response {TraceId}] {Method} {Path} - {StatusCode} - {Duration}ms";
        var responseFormatArgs = new ArrayList { traceId, context.Request.Method, context.Request.Path, context.Response.StatusCode, stopwatch.ElapsedMilliseconds };
        if (_logger.IsEnabled(LogLevel.Debug))
        {
            responseFormat += "\nBody: {Body}";
            responseFormatArgs.Add(responseBody);
        }

        _logger.LogInformation(responseFormat, responseFormatArgs.ToArray());
    }
}