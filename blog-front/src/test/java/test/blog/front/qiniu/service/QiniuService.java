package test.blog.front.qiniu.service;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;

import test.blog.front.qiniu.res.QiniuResponse;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

/**
 * 七牛云服务
 * @author ans
 *
 */
public class QiniuService
{
	private String access_key;
	private String secret_key;
	
	private static final String DOMAIN = "http://7xiari.com2.z0.glb.qiniucdn.com/";
	
	private Mac mac;
	
	public QiniuService(String access_key , String secret_key)
	{
		mac = new Mac(access_key, secret_key);
	}
	
	public QiniuService()
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 从七牛下载资源
	 * @param key
	 * @param targetFolder
	 */
	public void download(String key , String targetFolder)
	{
		DataInputStream in 		= null;
		FileOutputStream out 	= null;
		
		try
		{
			URL url = new URL(DOMAIN + URLEncoder.encode(key, "UTF-8"));
			in = new DataInputStream(url.openStream());
			out = new FileOutputStream(new File(targetFolder + key));  
			  
            byte[] buffer = new byte[1024];  
            int length;  

            while ((length = in.read(buffer)) > 0)
            {  
                out.write(buffer, 0, length);  
            }  
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				in.close();
				out.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}  
		}
	}
	
	/**
	 * 上传资源到七牛云
	 * @param bucketName
	 * @param key
	 * @param localFile
	 * @return
	 */
	public QiniuResponse upload(String bucketName , String key , String localFile)
	{
        PutPolicy putPolicy = new PutPolicy(bucketName + ":" + key);
        try
		{
			String uptoken = putPolicy.token(mac);
			PutExtra extra = new PutExtra();
	        PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
	        return new QiniuResponse(ret);
		} catch (AuthException e)
		{
			e.printStackTrace();
			return new QiniuResponse(false);
		} catch (JSONException e)
		{
			e.printStackTrace();
			return new QiniuResponse(false);
		}
	}

	public String getAccess_key()
	{
		return access_key;
	}

	public void setAccess_key(String access_key)
	{
		this.access_key = access_key;
	}

	public String getSecret_key()
	{
		return secret_key;
	}

	public void setSecret_key(String secret_key)
	{
		this.secret_key = secret_key;
	}

}
