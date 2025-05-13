using System.Security.Cryptography;
using System.Text;

namespace Sunny.Framework.External.Util
{
    public static class KSUtil
    {

        public static string SignatureReceive(string rawBody, string appSecret)
        {
            string signStr = rawBody + appSecret;
            byte[] inputBytes = Encoding.UTF8.GetBytes(signStr);
            byte[] hashBytes = MD5.HashData(inputBytes);
            return Convert.ToHexStringLower(hashBytes);
        }

        public static string SignatureRequest(Dictionary<string, object> param, string appSecret)
        {

            var trimmedParam = param.Where(item => !string.IsNullOrEmpty(item.Value.ToString())).ToDictionary(item => item.Key, item => item.Value);

            var sortedParam = trimmedParam.OrderBy(item => item.Key).ToDictionary(item => item.Key, item => item.Value);

            string paramStr = string.Join("&", sortedParam.Select(item => $"{item.Key}={item.Value}"));
            string signStr = paramStr + appSecret;

            byte[] inputBytes = Encoding.UTF8.GetBytes(signStr);
            byte[] hashBytes = MD5.HashData(inputBytes);
            return Convert.ToHexStringLower(hashBytes);
        }
    }
}
