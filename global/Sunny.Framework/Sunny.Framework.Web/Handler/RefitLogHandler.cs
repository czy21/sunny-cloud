using System.Collections;
using System.Diagnostics;
using Microsoft.Extensions.Logging;
using WishServer.Util;

namespace Sunny.Framework.Web.Middleware;

public class RefitLogHandler : DelegatingHandler
{
    private readonly ILogger<RefitLogHandler> _logger;

    public RefitLogHandler(ILogger<RefitLogHandler> logger)
    {
        _logger = logger;
    }

    protected override async Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
    {
        var traceId = Guid.NewGuid().ToString();
        var stopwatch = Stopwatch.StartNew();

        if (_logger.IsEnabled(LogLevel.Debug))
        {
            var headers = new SortedDictionary<string, string>();
            var requestBody = "";

            foreach (var t in request.Headers) headers.Add(t.Key, string.Join("; ", t.Value.ToList()));

            if (request.Content != null)
            {
                foreach (var t in request.Content.Headers) headers.Add(t.Key, string.Join("; ", t.Value.ToList()));
                if (_logger.IsEnabled(LogLevel.Debug))
                {
                    requestBody = await request.Content.ReadAsStringAsync(cancellationToken).ConfigureAwait(false);
                    if (headers.TryGetValue("Content-Type", out var requestContentType) && requestContentType.Contains("application/json", StringComparison.CurrentCultureIgnoreCase))
                    {
                        requestBody = JsonUtil.Serialize(JsonUtil.Deserialize<dynamic>(requestBody));
                    }
                }
            }

            var requestFormat = "[Refit Request {TraceId}] {Method} {Path} \nHeaders: {Headers}\nBody: {Body}";
            var requestFormatArgs = new ArrayList { traceId, request.Method, request.RequestUri, JsonUtil.Serialize(headers, true), requestBody };
            _logger.LogInformation(requestFormat, requestFormatArgs.ToArray());
        }

        var response = await base.SendAsync(request, cancellationToken).ConfigureAwait(false);

        var responseBody = "";
        if (_logger.IsEnabled(LogLevel.Debug))
        {
            responseBody = await response.Content.ReadAsStringAsync(cancellationToken).ConfigureAwait(false);
        }

        stopwatch.Stop();

        var responseFormat = "[Refit Response {TraceId}] {Method} {Path} - {StatusCode} - {Duration}ms";
        var responseFormatArgs = new ArrayList { traceId, request.Method, request.RequestUri, (int)response.StatusCode, stopwatch.ElapsedMilliseconds };

        if (_logger.IsEnabled(LogLevel.Debug))
        {
            responseFormat += "\nBody: {Body}";
            responseFormatArgs.Add(responseBody);
        }

        _logger.LogInformation(responseFormat, responseFormatArgs.ToArray());

        return response;
    }
}