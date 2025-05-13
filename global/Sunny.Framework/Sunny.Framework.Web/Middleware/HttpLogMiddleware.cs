using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
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
            context.Request.EnableBuffering();
            var requestBody = await new StreamReader(context.Request.Body).ReadToEndAsync();
            context.Request.Body.Position = 0;

            _logger.LogDebug("[HTTP Request] {Method} {Path}\nHeaders: {Headers}\nBody: {Body}",
                context.Request.Method,
                context.Request.Path,
                JsonUtil.Serialize(context.Request.Headers),
                context.Request.Headers.TryGetValue("Content-Type", out var requestContentType) && requestContentType.ToString().Contains("application/json", StringComparison.CurrentCultureIgnoreCase) ? JsonUtil.Serialize(JsonUtil.Deserialize<object>(requestBody)) : requestBody);

            var originalBodyStream = context.Response.Body;
            using var responseBody = new MemoryStream();
            context.Response.Body = responseBody;

            await _next(context);

            context.Response.Body.Seek(0, SeekOrigin.Begin);
            var responseText = await new StreamReader(context.Response.Body).ReadToEndAsync();
            context.Response.Body.Seek(0, SeekOrigin.Begin);

            _logger.LogDebug("[HTTP Response] {Method} {Path} - {StatusCode}\nBody: {Body}",
                context.Request.Method,
                context.Request.Path,
                context.Response.StatusCode,
                responseText);

            await responseBody.CopyToAsync(originalBodyStream);
        }
    }
}
