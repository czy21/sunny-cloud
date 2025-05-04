using EntityFrameworkCore.Scaffolding.Handlebars;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.Extensions.Options;

public class MyEntityTypeGenerator : HbsCSharpEntityTypeGenerator
{
    private Dictionary<IEntityType, Dictionary<string, object>> EntityTypeTemplateProperties = new Dictionary<IEntityType, Dictionary<string, object>>();

    public MyEntityTypeGenerator(IAnnotationCodeGenerator annotationCodeGenerator, ICSharpHelper cSharpHelper, IEntityTypeTemplateService entityTypeTemplateService, IEntityTypeTransformationService entityTypeTransformationService, IOptions<HandlebarsScaffoldingOptions> options) : base(annotationCodeGenerator, cSharpHelper, entityTypeTemplateService, entityTypeTransformationService, options)
    {

    }

    protected override void GenerateClass(IEntityType entityType)
    {
        base.GenerateClass(entityType);
        TemplateData.Add("entity-name", entityType.Name);
        TemplateData.Add("table-name", entityType.GetTableName());
        EntityTypeTemplateProperties.Add(entityType, TemplateData);
    }

    protected override void GenerateProperties(IEntityType entityType)
    {
        base.GenerateProperties(entityType);
        var propertyNameColumnNameDict = entityType.GetProperties().ToDictionary(p => p.Name, p => p.GetColumnName());
        List<Dictionary<string, object>> properties = (List<Dictionary<string, object>>)TemplateData["properties"];
        foreach (var t in propertyNameColumnNameDict)
        {
            foreach (var p in properties)
            {
                string propertyName = (string)p["property-name"];
                string propertyType = (string)p["property-type"];
                if (propertyName == t.Key)
                {
                    p["column-name"] = t.Value;
                }

                if (!propertyType.EndsWith("?"))
                {
                    p["property-type"] = propertyType + "?";
                }
            }
        }

        TemplateData.Add("primary-key-type", entityType.GetProperties().Where(t => t.IsPrimaryKey()).Select(t => base.CSharpHelper.Reference(t.ClrType)).FirstOrDefault());

        List<string> excludePropertyNames = ["Id", "CreateTime", "CreateUser", "UpdateTime", "UpdateUser", "Deleted"];
        properties.RemoveAll(t => excludePropertyNames.Contains(t["property-name"]));
    }

    public Dictionary<IEntityType, Dictionary<string, object>> GetEntityTypeTemplateProperties()
    {
        return EntityTypeTemplateProperties;
    }
}