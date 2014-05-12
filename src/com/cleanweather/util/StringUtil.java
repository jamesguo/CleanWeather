package com.cleanweather.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class StringUtil {
	public static ArrayList<String> formatData(String paramString) {
		ArrayList<String> arrayList = new ArrayList<String>();
		if (isEmpty(paramString)) {
			return arrayList;
		}
		StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "|");
		while (localStringTokenizer.hasMoreTokens()) {
			arrayList.add(localStringTokenizer.nextToken());
		}
		return arrayList;
	}

	public static void findLimitValue(ArrayList<String> paramList) {
		Double mMinData = Double.valueOf(Double.MAX_VALUE);
		Double mMaxData = Double.valueOf(Double.MIN_VALUE);
		Iterator<String> localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			Double localDouble = Double.valueOf(localIterator.next());
			if ((localDouble != null) && (localDouble.doubleValue() > mMaxData.doubleValue())) {
				mMaxData = localDouble;
			}
			if ((localDouble == null) || (localDouble.doubleValue() >= mMinData.doubleValue())) {
				mMinData = localDouble;
			}
		}
	}

	public static boolean isEmpty(String paramString) {
		// TODO Auto-generated method stub
		if ((paramString != null) && (paramString.trim().length() != 0) && (!paramString.equals("N/A"))) {
			return false;
		}
		return true;
	}

	public static String getWeatherText(String weaterId) {
		String weatherText = "";
		try {
			JSONObject jsonObject = new JSONObject(ConstantValue.WEATHER);
			JSONObject cn_JsonObject = jsonObject.optJSONObject("zh_cn");
			weatherText = cn_JsonObject.getString(weaterId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return weatherText;
	}

	public static String getWindText(String windId) {
		String windText = "";
		try {
			JSONObject jsonObject = new JSONObject(ConstantValue.WIND_DIRECTION);
			JSONObject cn_JsonObject = jsonObject.optJSONObject("zh_cn");
			windText = cn_JsonObject.getString(windId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return windText;
	}

	public static String getWindPowerText(String windPowerId) {
		String windPowerText = "";
		try {
			JSONObject jsonObject = new JSONObject(ConstantValue.WIND_FORCE);
			JSONObject cn_JsonObject = jsonObject.optJSONObject("zh_cn");
			windPowerText = cn_JsonObject.getString(windPowerId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return windPowerText;
	}

	public static boolean isExpires(String expiresTime) {
		boolean bool = false;
		if (isEmpty(expiresTime)) {
			return bool;
		}
		try {
			bool = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expiresTime).after(new Date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bool;
	}

	public static int getIntFromString(String str) {
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}

	public static Float getFloatFromString(String str) {
		try {
			return Float.valueOf(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1f;
	}

	public static void inputstreamtofile(InputStream ins, File file) {
		OutputStream os;
		try {

//			int length = ins.available();
//			byte[] buffer = new byte[length];
//			ins.read(buffer);
//			ins.close();
//			String res = EncodingUtils.getString(buffer, "UTF-8");
			
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = ins.read()) != -1) {
				bytestream.write(ch);
			}
			byte[] imgdata = bytestream.toByteArray();
			String str = EncodingUtils.getString(imgdata, "UTF-8");
			
//			os = new FileOutputStream(file);
//			int bytesRead = 0;
//			byte[] buffer = new byte[1024];
//			while ((bytesRead = ins.read(buffer)) != -1) {
//				os.write(buffer, 0, bytesRead);
//			}
//			os.close();
//			ins.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
