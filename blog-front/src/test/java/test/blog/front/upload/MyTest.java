package test.blog.front.upload;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  
 * 只是写的一个示例，filePath,和FileName根据需要进行调整。
 * 解决跨域问题
 */
public class MyTest {

	/* 上传文件至Server的方法 */
	  public static void main(String[] args) {
	    String end = "\r\n";
	    String twoHyphens = "--";
	    String boundary = "*****";
	    String newName = "test.jks";
	    String uploadFile = "d:/test.jks";
	    ;
	    String actionUrl = "http://localhost:8080/p/upload/upload";
	    try {
	      URL url = new URL(actionUrl);
	      HttpURLConnection con = (HttpURLConnection) url.openConnection();
	      /* 允许Input、Output，不使用Cache */
	      con.setDoInput(true);
	      con.setDoOutput(true);
	      con.setUseCaches(false);
	      /* 设置传送的method=POST */
	      con.setRequestMethod("POST");
	      /* setRequestProperty */
	      con.setRequestProperty("Connection", "Keep-Alive");
	      con.setRequestProperty("Charset", "UTF-8");
	      con.setRequestProperty("Content-Type",
	          "multipart/form-data;boundary=" + boundary);
	      /* 设置DataOutputStream */
	      DataOutputStream ds = new DataOutputStream(con.getOutputStream());
	      ds.writeBytes(twoHyphens + boundary + end);
	      ds.writeBytes("Content-Disposition: form-data; "
	          + "name=\"file\";filename=\"" + newName + "\"" + end);
	      ds.writeBytes(end);
	      /* 取得文件的FileInputStream */
	      FileInputStream fStream = new FileInputStream(uploadFile);
	      /* 设置每次写入1024bytes */
	      int bufferSize = 1024;
	      byte[] buffer = new byte[bufferSize];
	      int length = -1;
	      /* 从文件读取数据至缓冲区 */
	      while ((length = fStream.read(buffer)) != -1) {
	        /* 将资料写入DataOutputStream中 */
	        ds.write(buffer, 0, length);
	      }
	      ds.writeBytes(end);
	      ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	      /* close streams */
	      fStream.close();
	      ds.flush();
	      /* 取得Response内容 */
	      InputStream is = con.getInputStream();
	      int ch;
	      StringBuffer b = new StringBuffer();
	      while ((ch = is.read()) != -1) {
	        b.append((char) ch);
	      }
	      /* 将Response显示于Dialog */
	      System.out.println("上传成功" + b.toString().trim());
	      /* 关闭DataOutputStream */
	      ds.close();
	    } catch (Exception e) {
	    	System.out.println("上传失败" + e);
	    }
	  }

}