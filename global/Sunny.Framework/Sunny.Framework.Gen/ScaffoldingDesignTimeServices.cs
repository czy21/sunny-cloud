using EntityFrameworkCore.Scaffolding.Handlebars.Internal;
using HandlebarsDotNet;
using Microsoft.EntityFrameworkCore.Design;
using Microsoft.EntityFrameworkCore.Scaffolding;
using Microsoft.Extensions.DependencyInjection;
using System.Text.Json;

public class ScaffoldingDesignTimeServices : IDesignTimeServices
{
    public void ConfigureDesignTimeServices(IServiceCollection services)
    {
        services.AddHandlebarsScaffolding(options =>
        {
            options.ReverseEngineerOptions = ReverseEngineerOptions.DbContextAndEntities;
            options.TemplateData = new Dictionary<string, object>
            {
            };
        });

        var myHelper = (helperName: "json", helperFunction: (Action<EncodedTextWriter, Context, Arguments>)JsonHbsHelper);
        services.AddHandlebarsHelpers(myHelper);
        services.AddSingleton<IModelCodeGenerator, MyModelGenerator>();
        services.AddSingleton<ICSharpEntityTypeGenerator, MyEntityTypeGenerator>();
        services.AddHandlebarsTransformers2(entityTypeNameTransformer: t => t + "PO",entityFileNameTransformer: t => t + "PO");

    }

    private void JsonHbsHelper(EncodedTextWriter writer, Context context, Arguments arg3)
    {
        var json = System.Text.Json.JsonSerializer.Serialize(arg3[0], new JsonSerializerOptions { WriteIndented = true });
        writer.WriteSafeString(json);
    }
}