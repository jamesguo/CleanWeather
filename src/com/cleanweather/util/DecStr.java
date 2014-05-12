package com.cleanweather.util;

import java.io.UnsupportedEncodingException;

public class DecStr {
	private static String KEY = "Crackers and the thief will suffer misfortune";

	public static byte[] decrypt(byte[] paramArrayOfByte) {
		return encrypt(paramArrayOfByte);
	}

	public static byte[] encrypt(byte[] paramArrayOfByte) {
		try {
			byte[] arrayOfByte;
			int i;
			arrayOfByte = KEY.getBytes("UTF-8");
			i = arrayOfByte.length;
			int j = paramArrayOfByte.length;
			for (int k = 0, l = 0; k < j; k++, l++) {
				if (l == i) {
					l = 0;
				}
				paramArrayOfByte[k] = (byte) (paramArrayOfByte[k] ^ arrayOfByte[l]);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paramArrayOfByte;
	}
}
