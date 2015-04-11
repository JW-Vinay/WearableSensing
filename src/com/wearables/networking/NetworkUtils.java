package com.wearables.networking;

import java.util.HashMap;

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
		data.put("client_id", "8ee4d0aac5f64b28a75da8e48ba05de5");
		data.put("response_type", "code");
		data.put("redirect_uri", "http://codingthecrowd.com/class/abiyer/CuraTest/index.html");
		data.put("APIName", "OpenApiWeight+OpenApiBP+OpenApiSpO2+OpenApiBG+OpenApiActivity+OpenApiSleep");
		return data;
	}
}
