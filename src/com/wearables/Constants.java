package com.wearables;

public class Constants {

	public static final String INTENT_TASK_ACTION = "task_action";
	public static final String INTENT_DEV = "bluetooth_device";
	
	public static final String SHARED_FILE = "wearablesconf";
	
	public static final String CLIEND_ID = "8ee4d0aac5f64b28a75da8e48ba05de5";
	public static final String CLIENT_SECRET = "45d0831462294a63a950b60a064f7695";
	public static final String REDIRECT_URI = "http://codingthecrowd.com/class/abiyer/CuraTest/index.html";
	public static final String APIName = "OpenApiWeight+OpenApiBP+OpenApiSpO2+OpenApiBG+OpenApiActivity+OpenApiSleep";
	public static final String USER_AUTH_URL = "https://api.ihealthlabs.com:8443/OpenApiV2/OAuthv2/userauthorization/";
	
	public static enum SERVICE_ACTIONS
	{
		START_SERVICE, PAIR_DEVICE, CONNECT_DEVICE,
	}
}
