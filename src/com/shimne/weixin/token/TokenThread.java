package com.shimne.weixin.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.shimne.weixin.util.AccessTokenUtil;

/**
 * 定时获取微信access_token的线程
 * 
 */
public class TokenThread implements Runnable
{
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);

	public static String accessTokenUrl = "";
	public static String accessToken = "";

	public void run()
	{
		while (true)
		{
			try
			{
				String result = AccessTokenUtil.getAccessToken(accessTokenUrl);
				JSONObject jsonObject = JSONObject.parseObject(result);

				if (jsonObject != null)
				{
					accessToken = jsonObject.getString("access_token");
					log.info("获取accessToken成功。accessToken=" + accessToken);
					log.info("等待" + (jsonObject.getInteger("expires_in") / 2) + "秒后重新获取。");
					Thread.sleep((jsonObject.getInteger("expires_in") / 2) * 1000);
				}
				else
				{
					Thread.sleep(60 * 1000);
				}
			}
			catch (Exception e)
			{
				try
				{
					Thread.sleep(60 * 1000);
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}

				e.printStackTrace();
			}
		}
	}
}