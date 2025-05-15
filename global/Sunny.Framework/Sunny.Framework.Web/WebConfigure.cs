using System.Text.Encodings.Web;
using System.Text.Json;
using System.Text.Json.Serialization;
using Autofac.Extensions.DependencyInjection;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Routing;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Nacos.V2.DependencyInjection;
using NLog.Web;
using Refit;
using Sunny.Framework.Core.Json;
using WishServer.Util;

namespace Sunny.Framework.Web;

public static class WebConfigure
{
    public static IHostBuilder UseWebConfigure(this IHostBuilder builder)
    {
        builder.UseNacosConfig("NacosConfig");

        builder.ConfigureLogging(c => c.ClearProviders()).UseNLog();

        builder.UseServiceProviderFactory(new AutofacServiceProviderFactory());

        return builder;
    }

    public static IServiceCollection AddWebConfigure(this IServiceCollection services, IConfiguration config)
    {
        services.AddNacosV2Config(config, null, "NacosConfig");

        services.AddHostedService<NLogConfigListener>();
        services.AddControllers().AddJsonOptions(options =>
        {
            options.JsonSerializerOptions.Encoder = JavaScriptEncoder.UnsafeRelaxedJsonEscaping;
            options.JsonSerializerOptions.DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull;
            options.JsonSerializerOptions.Converters.Add(new DateTimeConverterUsingDateTimeParse("yyyy-MM-dd HH:mm:ss"));
        });

        services.AddSingleton(provider  =>
        {
            var jsonOptions = provider.GetService<IOptions<JsonOptions>>();
            return new RefitSettings
            {
                ContentSerializer = new SystemTextJsonContentSerializer(jsonOptions.Value.JsonSerializerOptions)
            };
        });

        services.AddHealthChecks();

        return services;
    }

    public static IEndpointConventionBuilder UseWebHealthCheck(this IEndpointRouteBuilder endpoints)
    {
        return endpoints.MapHealthChecks("/actuator/health");
    }
}