using System.Security.Cryptography;
using System.Text;

namespace Sunny.Framework.External.Util
{
    public static class DYUtil
    {
        public static string SignatureReceive(Dictionary<string, object> headers, string rawBody, string appSecretPush)
        {
            var sortedParam = headers.OrderBy(item => item.Key).ToDictionary(t => t.Key, item => item.Value);
            string paramStr = string.Join("&", sortedParam.Select(t => $"{t.Key}={t.Value}"));
            string signStr = paramStr + rawBody + appSecretPush;
            byte[] inputBytes = Encoding.UTF8.GetBytes(signStr);
            byte[] hashBytes = MD5.HashData(inputBytes);
            return Convert.ToBase64String(hashBytes);
        }
    }
}
