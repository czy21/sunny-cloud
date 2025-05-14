namespace Sunny.Framework.Core.Model;

public class CommonResult<T>
{
    public int Code { get; set; }
    public string Message { get; set; } = "success";
    public T Data { get; set; }

    public static CommonResult<T> Ok(T data, string message = "success")
    {
        return new CommonResult<T> { Data = data, Message = message };
    }

    public static CommonResult<T> Fail(int code, string message)
    {
        return new CommonResult<T> { Code = code, Message = message };
    }
}