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
        var stopwatch = Stopwatch.StartNew();

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

        var requestMsg = "[Refit Request] {Method} {Path}";
        var requestMsgArgs = new ArrayList { request.Method, request.RequestUri };
        
        if (_logger.IsEnabled(LogLevel.Debug))
        {
            requestMsg += "\nHeaders: {Headers}\nBody: {Body}";
            requestMsgArgs.AddRange(new ArrayList { JsonUtil.Serialize(headers, true), requestBody });
        }
        
        _logger.LogInformation(requestMsg, requestMsgArgs.ToArray());

        var response = await base.SendAsync(request, cancellationToken).ConfigureAwait(false);

        var responseBody = "";
        if (_logger.IsEnabled(LogLevel.Debug))
        {
            responseBody = await response.Content.ReadAsStringAsync(cancellationToken).ConfigureAwait(false);
        }

        stopwatch.Stop();
        
        var responseMsg = "[Refit Response] {Method} {Path} - {StatusCode} {Duration}ms";
        var responseMsgArgs = new ArrayList { request.Method, request.RequestUri, (int)response.StatusCode, stopwatch.ElapsedMilliseconds };
        
        if (_logger.IsEnabled(LogLevel.Debug))
        {
            responseMsg += "\nBody: {Body}";
            responseMsgArgs.Add(responseBody);
        }

        _logger.LogInformation(responseMsg, responseMsgArgs.ToArray());

        return response;
    }
}