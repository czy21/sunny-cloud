namespace Sunny.Framework.Core.Model
{
    public class CommonResult<T>
    {
        public int Code { get; set; } = 0;
        public string Message { get; set; } = "success";
        public T? Data { get; set; }

        public static CommonResult<T> Ok(T data, string message = "success") => new() { Data = data, Message = message };

        public static CommonResult<T> Fail(int code, string message) => new() { Code = code, Message = message };
    }
}
