using Refit;
using Sunny.Framework.External.Client.KS;

namespace Sunny.Framework.External.Client
{
    public interface IKSClient
    {
        [Headers("content-type: application/json")]
        [Get("/oauth2/access_token")]
        Task<KSAccessTokenRes> GetAccessToken([Query] KSAccessTokenReq param);

        [Headers("content-type: application/json")]
        [Post("/openapi/developer/live/smallPlay/bind")]
        Task<KSBindRes> Bind([Query("app_id")] string appId, [Query("access_token")] string accessToken, [Body] Dictionary<string, object> req);
    }
}
