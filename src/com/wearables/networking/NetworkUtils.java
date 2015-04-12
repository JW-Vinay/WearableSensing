package com.wearables.networking;

import java.util.HashMap;

import com.wearables.Constants;

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
		data.put("client_id", Constants.CLIEND_ID);
		data.put("response_type", "code");
		data.put("redirect_uri", Constants.REDIRECT_URI);
		data.put("APIName", Constants.APIName);
		return data;
	}
	
	public static HashMap<String , String> getAccesTokenParams(String code){
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("client_id", Constants.CLIEND_ID);
		data.put("client_secret", Constants.CLIENT_SECRET);
		data.put("grant_type", "authorization_code");
		data.put("redirect_uri", Constants.REDIRECT_URI);
		data.put("code", code);
		return data;
	}
}
