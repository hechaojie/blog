package test.blog.front.qiniu.res;


import com.qiniu.api.io.PutRet;

/**
 * 七牛云响应
 * @author ans
 *
 */
public class QiniuResponse
{
	public boolean OK = false;
	
	private String key;
	
	private int statusCode;
	
	private Exception ex;
	
	public QiniuResponse(PutRet ret)
	{
		OK = ret.ok();
		
		this.setKey(ret.getKey());
		this.setStatusCode(ret.getStatusCode());
		this.setEx(ret.getException());
	}
	
	public QiniuResponse(boolean ok)
	{
		OK = ok;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public int getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}

	public Exception getEx()
	{
		return ex;
	}

	public void setEx(Exception ex)
	{
		this.ex = ex;
	}
	
	@Override
	public String toString()
	{
		return "[Qiniu response is " + (OK == true ? "ok" : "no") + 
				" key = " + (key == null ? "null" : key) + 
				" statusCode = " + statusCode;
	}
}
