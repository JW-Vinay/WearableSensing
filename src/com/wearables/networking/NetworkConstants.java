package com.wearables.networking;

public class NetworkConstants {

	public static enum METHOD_TYPE
	{
		POST, GET, DELETE, PUT
	}
	
	public static enum REQUEST_URLS
	{
		POST_BIOMETRIC_ZEPHYR
	}
	
	public static final String REQ_PARAM_UNAME = "user_name";
	public final static String BASE_URL = "http://128.2.83.208:8001/";
	public final static String POST_BIOMETRIC_ENDPOINT = "api/v1/biometrics/";
}