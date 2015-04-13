package com.wearables.networking;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.wearables.models.BiometricSummaryModel;
import com.wearables.models.BiometricBreathingModel;
import com.wearables.models.BiometricECGModel;
import com.wearables.networking.NetworkConstants.METHOD_TYPE;
import com.wearables.networking.NetworkConstants.REQUEST_URLS;
import com.wearables.utils.LogUtils;

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
		data.put("client_id", "8ee4d0aac5f64b28a75da8e48ba05de5");
		data.put("response_type", "code");
		data.put("redirect_uri", "");
		
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
			JSONObject object = model.getJSON();
			object.put(NetworkConstants.REQ_PARAM_UNAME, "mshrimal");
			new NetworkingTask(url, false, METHOD_TYPE.POST, context, REQUEST_URLS.POST_BIOMETRIC_ZEPHYR).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, object);
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
			new NetworkingTask(url, false, METHOD_TYPE.POST, context, REQUEST_URLS.POST_BIOMETRIC_ZEPHYR).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, object);
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
			new NetworkingTask(url, false, METHOD_TYPE.POST, context, REQUEST_URLS.POST_BIOMETRIC_ZEPHYR).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, object);
		}
		catch(JSONException e)
		{
			LogUtils.LOGE(TAG, "" + e.getMessage());
		}
		
	}
	
	
}
