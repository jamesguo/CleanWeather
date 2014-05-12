package com.cleanweather.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipStr {
	public static byte[] compress(byte[] paramArrayOfByte) {
		if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
			return paramArrayOfByte;
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		try {
			GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream);
			localGZIPOutputStream.write(paramArrayOfByte);
			localGZIPOutputStream.close();
			paramArrayOfByte = localByteArrayOutputStream.toByteArray();
		} catch (IOException localIOException) {
			paramArrayOfByte = null;
		} catch (Exception localException) {
			paramArrayOfByte = null;
		}
		return paramArrayOfByte;
	}

	public static byte[] uncompress(byte[] paramArrayOfByte) {
		if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
			return paramArrayOfByte;
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
		try {
			GZIPInputStream localGZIPInputStream = new GZIPInputStream(localByteArrayInputStream);
			byte[] arrayOfByte = new byte[256];
			int i = 0;
			while ((i = localGZIPInputStream.read(arrayOfByte)) != -1) {    
				localByteArrayOutputStream.write(arrayOfByte, 0, i); 
		    }
			paramArrayOfByte = localByteArrayOutputStream.toByteArray();
		} catch (IOException localIOException) {
			paramArrayOfByte = null;
		} catch (Exception localException) {
			paramArrayOfByte = null;
		}
		return paramArrayOfByte;
	}
}
