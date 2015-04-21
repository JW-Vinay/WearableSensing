package com.wearables.networking;

public class NetworkConstants {

	public static enum METHOD_TYPE
	{
		POST, GET, DELETE, PUT
	}
	
	public static enum REQUEST_TYPE
	{
		POST_BIOMETRIC_ZEPHYR, ACCESS_TOKEN, SP02, BP, REFRESH_TOKEN_BP, REFRESH_TOKEN_BO, POST_PIP, ACCESS_TOKEN_SPO2, ACCESS_TOKEN_BP, POST_WITHINGS
	}
	
	public static final String ACCESS_TOKEN = "access_token";
	public static final String USER_ID = "UserID";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String TIMESTAMP = "timestamp";
	
	public static final String CLIENT_ID = "8ee4d0aac5f64b28a75da8e48ba05de5";
	public static final String CLIENT_SECRET = "45d0831462294a63a950b60a064f7695";
	public static final String REDIRECT_URI = "http://codingthecrowd.com/class/abiyer/CuraTest/index.html";
	public static final String APIName = "OpenApiWeight+OpenApiBP+OpenApiSpO2+OpenApiBG+OpenApiActivity+OpenApiSleep";
	public static final String USER_AUTH_URL = "https://api.ihealthlabs.com:8443/OpenApiV2/OAuthv2/userauthorization/";
	public static final String GET_BIODATA_URL = "https://api.ihealthlabs.com:8443/openapiv2/user/";
	public static final String RESPONSE_TYPE = "response_type";
	
	public static final String SC = "sc";
	public static final String SV = "sv";
	
	public static final String SC_VALUE = "6951b55bbcf14f44923e8e3a653eded6";
	
	/* SPO2 sv */
	public static final String SPO2_SV = "a7259f44e4ac44cd9a216f5b5b4f66ee";
	
	/* BP sv*/
	public static final String BP_SV = "e2f1faf965134081aab954e2b2555b58";

	/* Withings auth */
	public static final String CONSUMER_KEY = "cf0d262768d16bf5f162768ddf0fef3038237f89539ee096c53f784cc9411";
	public static final String CONSUMER_SECRET = "918de5c8eb71fc0e2260c8117185773db81c4706cfb727c4524cf96";
	
	public static final String CALLBACK_URL = "http://my.withings.com/en/utils/graph?userid=7109101&publickey=36525c7a7de36cc7&massUnit=lb&forcedisplay=fm";
	public static final String WITHINGS_URL_DATA = "http://wbsapi.withings.net/measure?action=getmeas&oauth_consumer_key=cf0d262768d16bf5f162768ddf0fef3038237f89539ee096c53f784cc9411&oauth_nonce=3b5f6ada7d7147d8fa4a2786aab1df49&oauth_signature=OtHDuQ%2BHw0joHcRZynpsXXtQLmY%3D&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1429592489&oauth_token=ef8238d382e0b4796c4ccccfb2d51f287ffc2322c1190cecc01d83c21&oauth_version=1.0&userid=7109101";
	
	public static final String REQ_PARAM_UNAME = "user_name";
	public final static String BASE_URL = "http://128.2.83.208:8001/";
	public final static String POST_BIOMETRIC_ENDPOINT = "api/v1/biometrics/";
	public final static String POST_BIOMETRIC_PRECISE_ENDPOINT = "/api/v1/biometricsprecise/";
	public final static String POST_PIP_DATA_ENDPOINT = "api/v1/stress/";
	public final static String POST_WITHINGS_DATA_ENDPOINT = "api/v1/weight/";
	public final static String HOME_DIALYSIS_ENDPOINT = "http://rpcs.herokuapp.com/";
	public final static String POST_BIOMETRIC_HD = "api/v1/Biometrics";
}
