using Refit;
using Sunny.Framework.External.Client.DY;

namespace Sunny.Framework.External.Client
{
    public interface DYOAuthClient
    {
        [Headers("content-type: application/json")]
        [Post("/apps/v2/token")]
        Task<DYAccessTokenRes> GetAccessToken([Body] DYAccessTokenReq param);
    }
}