using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Nacos.V2.DependencyInjection;
using NLog.Web;

namespace Sunny.Framework.Web
{
    public static class WebConfigure
    {
        public static IHostBuilder UseWebConfigure(this IHostBuilder builder)
        {
            builder.UseNacosConfig("NacosConfig");

            builder.ConfigureLogging(c => c.ClearProviders()).UseNLog();

            return builder;
        }

        public static IServiceCollection AddWebConfigure(this IServiceCollection services, IConfiguration config)
        {
            services.AddNacosV2Config(config, null, "NacosConfig");
            return services;
        }
    }
}
