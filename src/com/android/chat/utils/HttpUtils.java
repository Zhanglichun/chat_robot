package com.android.chat.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

import android.content.Context;
import android.widget.Toast;

import com.android.chat.bean.ChatMsg;
import com.android.chat.bean.ChatMsg.Type;
import com.android.chat.bean.Result;
import com.android.chat.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class HttpUtils {
	private static final String URL = "http://www.tuling123.com/openapi/api";

	/*
	 * 发送一个消息，得到chatMsg对象。
	 * 
	 */
	public static ChatMsg sendMsg(String msg){
		
		ChatMsg chatMsg = new ChatMsg();
		String resultStr = doGet(msg);
		
		System.out.println(resultStr);
		
		Gson gson = new Gson();
		Result result = null;
		try {
			result = gson.fromJson(resultStr, Result.class);
			
			chatMsg.setMsg(result.getText());
			
		} catch (JsonSyntaxException e) {

			chatMsg.setMsg("服务器异常");
			e.printStackTrace();
		}
		
		chatMsg.setDate(new Date());
		chatMsg.setType(Type.INCOMING);
		
		
		return chatMsg;
	}
	
	private static String doGet(String msg) {

		String result = "";

		String url = setParams(msg);

		InputStream is = null;

		ByteArrayOutputStream byteArrayOutputStream = null;

		try {
			java.net.URL urlNet = new java.net.URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlNet
					.openConnection();
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			
			int returnCode = connection.getResponseCode();
			is = connection.getInputStream();
			
			
			int length = -1;
			byte[] buf = new byte[1024];
			byteArrayOutputStream = new ByteArrayOutputStream();
			while ((length = is.read(buf)) != -1) {
				byteArrayOutputStream.write(buf, 0, length);

			}
			byteArrayOutputStream.flush();
			result = new String(byteArrayOutputStream.toByteArray());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	} 

	private static String setParams(String msg) {

		String url = "";
		
		try {
			url = URL + "?key=" + Constant.KEY + "&info=" + URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
}
