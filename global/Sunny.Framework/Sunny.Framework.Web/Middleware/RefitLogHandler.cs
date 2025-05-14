using System.Net;

namespace Sunny.Framework.Web.Middleware
{
    using Microsoft.Extensions.Logging;
    using System.Collections.Generic;
    using System.Diagnostics;
    using System.Net.Http;
    using WishServer.Util;

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
            string requestBody = null;

            foreach (var t in request.Headers)
            {
                headers.Add(t.Key, string.Join("; ", t.Value.ToList()));
            }

            if (request.Content != null)
            {
                foreach (var t in request.Content.Headers)
                {
                    headers.Add(t.Key, string.Join("; ", t.Value.ToList()));
                }

                requestBody = await request.Content.ReadAsStringAsync(cancellationToken).ConfigureAwait(false);
            }

            _logger.LogDebug("[Refit Request] {Method} {Path}\nHeaders: {Headers}\nBody: {Body}",
                request.Method,
                request.RequestUri,
                JsonUtil.Serialize(headers, true),
                headers.TryGetValue("Content-Type", out var requestContentType) && requestContentType.Contains("application/json", StringComparison.CurrentCultureIgnoreCase) ? JsonUtil.Serialize(JsonUtil.Deserialize<object>(requestBody), true) : requestBody
            );

            var response = await base.SendAsync(request, cancellationToken).ConfigureAwait(false);

            var responseBody = await response.Content.ReadAsStringAsync(cancellationToken).ConfigureAwait(false);

            stopwatch.Stop();
            _logger.LogDebug("[Refit Response] {Method} {Path} - {StatusCode} {Duration}ms \nBody: {Body}",
                request.Method,
                request.RequestUri,
                (int)response.StatusCode,
                stopwatch.ElapsedMilliseconds,
                responseBody
            );

            return response;
        }
    }
}