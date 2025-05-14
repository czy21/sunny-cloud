using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using System.Diagnostics;
using System.Text;
using WishServer.Util;

namespace Sunny.Framework.Web.Middleware
{
    public class HttpLogMiddleware
    {
        private readonly RequestDelegate _next;
        private readonly ILogger<HttpLogMiddleware> _logger;

        public HttpLogMiddleware(RequestDelegate next, ILogger<HttpLogMiddleware> logger)
        {
            _next = next;
            _logger = logger;
        }

        public async Task Invoke(HttpContext context)
        {
            var stopwatch = Stopwatch.StartNew();

            context.Request.EnableBuffering();
            var requestBody = await new StreamReader(context.Request.Body).ReadToEndAsync();
            context.Request.Body.Position = 0;

            _logger.LogDebug("[HTTP Request] {Method} {Path}\nHeaders: {Headers}\nBody: {Body}",
                context.Request.Method,
                context.Request.Path,
                JsonUtil.Serialize(context.Request.Headers.ToDictionary(t => t.Key, t => string.Join("; ", t.Value.ToArray())), true),
                context.Request.Headers.TryGetValue("Content-Type", out var requestContentType) && requestContentType.ToString().Contains("application/json", StringComparison.CurrentCultureIgnoreCase) ? JsonUtil.Serialize(JsonUtil.Deserialize<object>(requestBody), true) : requestBody);

            var originalBodyStream = context.Response.Body;
            using var responseBody = new MemoryStream();
            context.Response.Body = responseBody;

            await _next(context);

            context.Response.Body.Seek(0, SeekOrigin.Begin);
            var responseText = await new StreamReader(context.Response.Body).ReadToEndAsync();
            context.Response.Body.Seek(0, SeekOrigin.Begin);

            await responseBody.CopyToAsync(originalBodyStream);

            stopwatch.Stop();

            _logger.LogDebug("[HTTP Response] {Method} {Path} - {StatusCode} {Duration}ms \nBody: {Body}",
                context.Request.Method,
                context.Request.Path,
                context.Response.StatusCode,
                stopwatch.ElapsedMilliseconds,
                responseText);
        }
    }
}
