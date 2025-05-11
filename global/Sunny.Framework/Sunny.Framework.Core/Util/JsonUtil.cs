using Sunny.Framework.Core.Json;
using System.Text.Encodings.Web;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace WishServer.Util
{
    public class JsonUtil
    {

        public static readonly JsonSerializerOptions JSON_SERIALIZER_OPTIONS = new JsonSerializerOptions(JsonSerializerDefaults.Web)
        {
            Encoder = JavaScriptEncoder.UnsafeRelaxedJsonEscaping,
            DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
        };

        static JsonUtil()
        {
            JSON_SERIALIZER_OPTIONS.Converters.Add(new DateTimeConverterUsingDateTimeParse("yyyy-MM-dd HH:mm:ss"));
        }

        public static string Serialize<TValue>(TValue value)
        {
            return JsonSerializer.Serialize(value, JSON_SERIALIZER_OPTIONS);
        }

        public static TValue? Deserialize<TValue>(string json)
        {
            return JsonSerializer.Deserialize<TValue>(json, JSON_SERIALIZER_OPTIONS);
        }

        public static TValue? Deserialize<TValue>(JsonElement element)
        {
            return JsonSerializer.Deserialize<TValue>(element, JSON_SERIALIZER_OPTIONS);
        }

        public static object? Deserialize(JsonElement element, Type returnType)
        {
            return JsonSerializer.Deserialize(element, returnType, JSON_SERIALIZER_OPTIONS);
        }
    }
}
