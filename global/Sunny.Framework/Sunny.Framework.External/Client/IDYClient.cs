using Refit;
using Sunny.Framework.External.Client.DY;

namespace Sunny.Framework.External.Client
{
    public interface IDYClient
    {
        // 直播信息 https://developer.open-douyin.com/docs/resource/zh-CN/interaction/develop/server/live/webcastinfo
        [Headers("content-type: application/json")]
        [Post("/webcastmate/info")]
        Task<DYWebCastInfoRes> GetLiveInfo([Body] DYWebCastInfoReq param, [Header("X-Token")] string accessToken);

        #region 消息推送任务: https://bytedance.larkoffice.com/wiki/wikcnQe5jesCAbyUzsGx8xQeBNh
        [Headers("content-type: application/json")]
        [Post("/live_data/task/start")]
        Task<DYLiveDataTaskRes> StartTaskPush([Body] DYLiveDataTaskReq param, [Header("X-Token")] string accessToken);

        [Headers("content-type: application/json")]
        [Post("/live_data/task/stop")]
        Task<DYLiveDataTaskRes> StopTaskPush([Body] DYLiveDataTaskReq param, [Header("X-Token")] string accessToken);

        [Headers("content-type: application/json")]
        [Post("/live_data/task/get")]
        Task<DYLiveDataTaskRes> GetTaskStatus([Body] DYLiveDataTaskReq param, [Header("X-Token")] string accessToken);
        #endregion

        // 履约上报: https://developer.open-douyin.com/docs/resource/zh-CN/interaction/develop/server/live/ack-ability
        [Headers("content-type: application/json")]
        [Post("/live_data/ack")]
        Task<DYLiveDataAckRes> Ack([Body] DYLiveDataAckReq param, [Header("access-token")] string accessToken);

        #region 用户战绩与排行榜: https://developer.open-douyin.com/docs/resource/zh-CN/interaction/develop/server/live/user-records-rankings
        // 设置当前生效的世界榜单版本
        [Headers("content-type: application/json")]
        [Post("/gaming_con/world_rank/set_valid_version")]
        Task<DYWebCastResult> WorldSetValidVersion([Body] DYWorldSetValidVersionReq param, [Header("X-Token")] string accessToken);

        // 上传世界榜单列表数据
        [Headers("content-type: application/json")]
        [Post("/gaming_con/world_rank/upload_rank_list")]
        Task<DYWebCastResult> WorldUploadRankList([Body] DYWorldUploadRankReq param, [Header("X-Token")] string accessToken);

        // 上报用户世界榜单的累计战绩
        [Headers("content-type: application/json")]
        [Post("/gaming_con/world_rank/upload_user_result")]
        Task<DYWebCastResult> WorldUploadUserResult([Body] DYWorldUploadUserReq param, [Header("X-Token")] string accessToken);

        // 完成用户世界榜单的累计战绩上报
        [Headers("content-type: application/json")]
        [Post("/gaming_con/world_rank/complete_upload_user_result")]
        Task<DYWebCastResult> WorldCompleteUploadUserResult([Body] DYWorldUploadUserCompleteReq param, [Header("X-Token")] string accessToken);

        // 同步对局状态
        [Headers("content-type: application/json")]
        [Post("/gaming_con/round/sync_status")]
        Task<DYWebCastResult> RoundSync([Body] DYRoundSyncStatusReq param, [Header("X-Token")] string accessToken);

        // 上报对局榜单列表
        [Headers("content-type: application/json")]
        [Post("/gaming_con/round/upload_rank_list")]
        Task<DYWebCastResult> RoundUploadRankList([Body] DYRoundUploadRankReq param, [Header("X-Token")] string accessToken);

        // 上报用户对局数据
        [Headers("content-type: application/json")]
        [Post("/gaming_con/round/upload_user_result")]
        Task<DYWebCastResult> RoundUploadUserResult([Body] DYRoundUploadUserReq param, [Header("X-Token")] string accessToken);

        // 完成用户对局数据上报
        [Headers("content-type: application/json")]
        [Post("/gaming_con/round/complete_upload_user_result")]
        Task<DYWebCastResult> RoundCompleteUploadUserResult([Body] DYRoundUploadUserCompleteReq param, [Header("X-Token")] string accessToken);
        #endregion
    }
}
