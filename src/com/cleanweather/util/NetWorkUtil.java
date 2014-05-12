package com.cleanweather.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class NetWorkUtil {
	private static final int CONNECTION_TIMEOUT = 20000;
	private static final int SO_TIMEOUT = 20000;

	public NetWorkUtil() {
	}

	public static byte[] httpPost(String url, byte[] paramStr) {
		byte[] imgdata = null;
		try {

			DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
			localDefaultHttpClient.getParams().setParameter("http.connection.timeout", CONNECTION_TIMEOUT);
			localDefaultHttpClient.getParams().setParameter("http.socket.timeout", SO_TIMEOUT);
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/octet-stream");
			ByteArrayEntity arrayEntity = new ByteArrayEntity(paramStr);
			post.setEntity(arrayEntity);
			HttpResponse response = localDefaultHttpClient.execute(post);
			HttpEntity entity = response.getEntity();
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			InputStream inputStream = entity.getContent();
			int ch;
			while ((ch = inputStream.read()) != -1) {
				bytestream.write(ch);
			}
			imgdata = bytestream.toByteArray();
			bytestream.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgdata;
	}

	private static byte[] httpGet(String url) {
		// TODO Auto-generated method stub
		byte[] imgdata = null;
		try {
			DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
			localDefaultHttpClient.getParams().setParameter("http.connection.timeout", CONNECTION_TIMEOUT);
			localDefaultHttpClient.getParams().setParameter("http.socket.timeout", SO_TIMEOUT);
			HttpGet post = new HttpGet(url);
			HttpResponse response = localDefaultHttpClient.execute(post);
			HttpEntity entity = response.getEntity();
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			InputStream inputStream = entity.getContent();
			int ch;
			while ((ch = inputStream.read()) != -1) {
				bytestream.write(ch);
			}
			imgdata = bytestream.toByteArray();
			bytestream.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgdata;
	}

	public static String get(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
		byte[] arrayOfByte1;
		String str;
		try {
			arrayOfByte1 = httpGet(paramString);
			if (paramBoolean1) {
				arrayOfByte1 = DecStr.decrypt(arrayOfByte1);
			}
			if (paramBoolean2) {
				arrayOfByte1 = ZipStr.uncompress(arrayOfByte1);
			}
			str = new String(arrayOfByte1, "UTF-8");
		} catch (Exception localException) {
			str = "error";
		}
		return str;
	}

	public static String post(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
		byte[] arrayOfByte2;
		String str;
		try {
			byte[] arrayOfByte1 = paramString2.getBytes("UTF-8");
			if (paramBoolean1) {
				arrayOfByte1 = ZipStr.compress(arrayOfByte1);
			}
			if (paramBoolean2) {
				arrayOfByte1 = DecStr.encrypt(arrayOfByte1);
			}
			arrayOfByte2 = httpPost(paramString1, arrayOfByte1);
			if (paramBoolean3) {
				arrayOfByte2 = DecStr.decrypt(arrayOfByte2);
			}
			if (paramBoolean4) {
				arrayOfByte2 = ZipStr.uncompress(arrayOfByte2);
			}
			str = new String(arrayOfByte2, "UTF-8");
		} catch (Exception localException) {
			str = "error";
		}
		return str;
	}
}
