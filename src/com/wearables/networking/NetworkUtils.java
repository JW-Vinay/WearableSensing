package com.wearables.networking;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.wearables.models.BiometricBreathingModel;
import com.wearables.models.BiometricECGModel;
import com.wearables.models.BiometricSummaryModel;
import com.wearables.networking.NetworkConstants.METHOD_TYPE;
import com.wearables.networking.NetworkConstants.REQUEST_TYPE;
import com.wearables.utils.LogUtils;
import com.wearables.utils.SharedPrefs;

public class NetworkUtils {

	private static final String TAG = "NetworkUtils";
	
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
	
	/**
	 * Post biometric summary data
	 * @param context
	 * @param model
	 * @param url
	 */
	public static void postBiometricData(Context context, BiometricSummaryModel model)
	{
		try
		{
			String url = NetworkConstants.BASE_URL + NetworkConstants.POST_BIOMETRIC_ENDPOINT;
			String hdUrl = NetworkConstants.HOME_DIALYSIS_ENDPOINT + NetworkConstants.POST_BIOMETRIC_HD;
			JSONObject object = model.getJSON();
			JSONObject hdObject = model.getHDJSON();
			object.put(NetworkConstants.REQ_PARAM_UNAME, "mshrimal");
			hdObject.put(NetworkConstants.REQ_PARAM_UNAME, "mshrimal");
			new NetworkingTask(url, false, METHOD_TYPE.POST, REQUEST_TYPE.POST_BIOMETRIC_ZEPHYR, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, object);
			new NetworkingTask(hdUrl, false, METHOD_TYPE.POST, REQUEST_TYPE.POST_BIOMETRIC_ZEPHYR, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, hdObject);
		}
		catch(JSONException e)
		{
			LogUtils.LOGE(TAG, "" + e.getMessage());
		}		
	}
	
	/**
	 * Post ecg waveform data
	 * @param context
	 * @param model
	 * @param url
	 */
	public static void postBiometricData(Context context, BiometricECGModel model)
	{
		try
		{
			String url = NetworkConstants.BASE_URL + NetworkConstants.POST_BIOMETRIC_ENDPOINT;
			JSONObject object = model.getJSON();
			object.put(NetworkConstants.REQ_PARAM_UNAME, "mshrimal");
			new NetworkingTask(url, false, METHOD_TYPE.POST, REQUEST_TYPE.POST_BIOMETRIC_ZEPHYR, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, object);
		}
		catch(JSONException e)
		{
			LogUtils.LOGE(TAG, "" + e.getMessage());
		}
		
	}
	
	/**
	 * Post breathing waveform data
	 * @param context
	 * @param model
	 * @param url
	 */
	public static void postBiometricData(Context context, BiometricBreathingModel model)
	{
		try
		{
			String url = NetworkConstants.BASE_URL + NetworkConstants.POST_BIOMETRIC_ENDPOINT;
			JSONObject object = model.getJSON();
			object.put(NetworkConstants.REQ_PARAM_UNAME, "mshrimal");
			new NetworkingTask(url, false, METHOD_TYPE.POST, REQUEST_TYPE.POST_BIOMETRIC_ZEPHYR, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, object);
		}
		catch(JSONException e)
		{
			LogUtils.LOGE(TAG, "" + e.getMessage());
		}
		
	}
	
	/**
	 * Post stress data
	 * @param context
	 * @param object
	 */
	public static void postStressMeasurementData(Context context, JSONObject object)
	{
		String url = NetworkConstants.BASE_URL + NetworkConstants.POST_PIP_DATA;
//		url  = "http://128.2.83.208:8001/api/v1/stress/";
		new NetworkingTask(url, false, METHOD_TYPE.POST, REQUEST_TYPE.POST_PIP, context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, object);
	}
	
}
