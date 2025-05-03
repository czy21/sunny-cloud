using Microsoft.EntityFrameworkCore.Design;
using Microsoft.Extensions.DependencyInjection;
using EntityFrameworkCore.Scaffolding.Handlebars;
using System.Text.Json;
using HandlebarsDotNet;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Scaffolding.Internal;
using Microsoft.EntityFrameworkCore.Scaffolding.Metadata;

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
    }

    private void JsonHbsHelper(EncodedTextWriter writer, Context context, Arguments arg3)
    {
        var json = System.Text.Json.JsonSerializer.Serialize(arg3[0], new JsonSerializerOptions { WriteIndented = true });
        writer.WriteSafeString(json);
    }
}