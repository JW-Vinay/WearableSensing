package com.wearables.utils;

public class Constants {

	public static final String INTENT_TASK_ACTION = "task_action";
	public static final String INTENT_DEV = "bluetooth_device";
	public static final String INTENT_SUMMARY = "summary";
	public static final String INTENT_SUMMARY_MODEL = "summary_model";
	public static final String INTENT_BREATHING = "breathing";
	public static final String INTENT_BREATHING_MODEL = "breathing_model";	
	public static final String INTENT_ECG = "ecg";
	public static final String INTENT_ECG_MODEL = "ecg_model";	
	
	public static enum SERVICE_ACTIONS
	{
		START_SERVICE, PAIR_DEVICE, CONNECT_DEVICE, DISCONNECT_DEVICE
	}
	
	public static final long INTERVAL_MILLIS = 5 * 1000;
	
	public static final String SHARED_FILE = "wearablesconf";
	
	public static final String CLIEND_ID = "8ee4d0aac5f64b28a75da8e48ba05de5";
	public static final String CLIENT_SECRET = "45d0831462294a63a950b60a064f7695";
	public static final String REDIRECT_URI = "http://codingthecrowd.com/class/abiyer/CuraTest/index.html";
	public static final String APIName = "OpenApiWeight+OpenApiBP+OpenApiSpO2+OpenApiBG+OpenApiActivity+OpenApiSleep";
	public static final String USER_AUTH_URL = "https://api.ihealthlabs.com:8443/OpenApiV2/OAuthv2/userauthorization/";
	public static final int EXPIRY_TIME = 10 * 60 * 1000;
//	public static final long INITIAL_START_TIME = 30 * 1000;
}
