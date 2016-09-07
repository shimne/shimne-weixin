package com.shimne.weixin.util;

import java.security.MessageDigest;
import java.util.Arrays;

public class SignUtil
{
	/**
	 * 微信认证请求时验证签名是否成功
	 * 
	 * @param token
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	public static boolean checkSignature(String token, String signature, String timestamp, String nonce, String echostr)
	{
		String[] arr = new String[] {token, timestamp, nonce};
		Arrays.sort(arr);

		StringBuilder content = new StringBuilder();

        for (int i = 0; i < arr.length; i++)
        {  
            content.append(arr[i]);  
        }  

        try
        {
        	MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			byte[] byteArray = messageDigest.digest(content.toString().getBytes());
			String s = byteToString(byteArray);

			if (s.equals(signature.toUpperCase()))
			{
				return true;
			}
		}
        catch (Exception e)
        {
			e.printStackTrace();
		}
        
		return false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToString(byte[] byteArray)
	{
		String s = "";

		for (int i = 0; i < byteArray.length; i++)
		{
			s += byteToHex(byteArray[i]);
		}

		return s;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHex(byte b)
	{
		char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		char[] temp = new char[2];

		temp[0] = digit[(b >>> 4) & 0X0F];
		temp[1] = digit[b & 0X0F];

		return new String(temp);
	}
}