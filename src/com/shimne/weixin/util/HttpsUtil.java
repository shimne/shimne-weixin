package com.shimne.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * HTTPS请求工具
 */
public class HttpsUtil
{
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return String 返回请求数据
	 */
	public static String gethttpRequest(String requestUrl, String requestMethod, String outputStr)
	{
		StringBuffer buffer = new StringBuffer();

		try
		{
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
			{
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (outputStr != null && outputStr.trim().length() > 0)
			{
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null)
			{
				buffer.append(str);
			}

			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

			return buffer.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "";
	}
}