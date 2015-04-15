package com.wearables.networking;

import java.util.HashMap;

import android.content.Context;

import com.wearables.utils.SharedPrefs;

public class NetworkUtils {

	public static String generateUrl(String baseUrl, HashMap<String, String> mData)
	{
	
		String params = "";
		for(String key : mData.keySet())
		{
			params += key + "=" + mData.get(key) + "&";
		}
		
		params = params.substring(0, params.length()-1);
		return baseUrl+"?"+params;
	}
	
	public static HashMap<String, String> getAuthorizationParams()
	{
		//TODO: fragment this further to ensure common params are added in only 1 method and invoke them
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("client_id", NetworkConstants.CLIENT_ID);
		data.put("response_type", "code");
		data.put("redirect_uri", NetworkConstants.REDIRECT_URI);
		data.put("APIName", NetworkConstants.APIName);
		return data;
	}
	
	public static HashMap<String , String> getAccessTokenParams(String code){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("client_id", NetworkConstants.CLIENT_ID);
		data.put("client_secret", NetworkConstants.CLIENT_SECRET);
		data.put("grant_type", "authorization_code");
		data.put("redirect_uri", NetworkConstants.REDIRECT_URI);
		data.put("code", code);
		return data;
	}
	
	public static HashMap<String, String> getDataParams(String accessToken, String svVal){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("client_id", NetworkConstants.CLIENT_ID);
		data.put("client_secret", NetworkConstants.CLIENT_SECRET);
		data.put("redirect_uri", NetworkConstants.REDIRECT_URI);
		data.put(NetworkConstants.ACCESS_TOKEN, accessToken);
		data.put(NetworkConstants.SC, NetworkConstants.SC_VALUE);
		data.put(NetworkConstants.SV, svVal);
		return data;
	}
	
	public static HashMap<String , String> getRefreshTokenParams(Context context){
		HashMap<String, String> data = new HashMap<String, String>();
		SharedPrefs sp = SharedPrefs.getInstance(context);
		
		data.put("client_id", NetworkConstants.CLIENT_ID);
		data.put("client_secret", NetworkConstants.CLIENT_SECRET);
		data.put("redirect_uri", NetworkConstants.REDIRECT_URI);
		data.put(NetworkConstants.REFRESH_TOKEN, sp.getParameters(NetworkConstants.REFRESH_TOKEN));
		data.put("response_type", NetworkConstants.REFRESH_TOKEN);
		data.put(NetworkConstants.USER_ID, sp.getParameters(NetworkConstants.USER_ID));
		return data;
	}
}
