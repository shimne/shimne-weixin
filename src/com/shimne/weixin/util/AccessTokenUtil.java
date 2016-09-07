package com.shimne.weixin.util;

public class AccessTokenUtil
{
	/**
	 * ªÒ»°access_token
	 * 
	 * @param accessTokenUrl 
	 * @return String
	 */
	public static String getAccessToken(String accessTokenUrl)
	{
		return HttpsUtil.gethttpRequest(accessTokenUrl, "GET", null);
	}
}