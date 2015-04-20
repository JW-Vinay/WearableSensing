package com.wearables.networking;

public class NetworkConstants {

	public static enum METHOD_TYPE
	{
		POST, GET, DELETE, PUT
	}
	
	public static enum REQUEST_TYPE
	{
		POST_BIOMETRIC_ZEPHYR, ACCESS_TOKEN, SP02, BP, REFRESH_TOKEN_BP, REFRESH_TOKEN_BO, POST_PIP, ACCESS_TOKEN_SPO2, ACCESS_TOKEN_BP
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

	
	public static final String REQ_PARAM_UNAME = "user_name";
	public final static String BASE_URL = "http://128.2.83.208:8001/";
	public final static String POST_BIOMETRIC_ENDPOINT = "api/v1/biometrics/";
	public final static String POST_PIP_DATA = "api/v1/stress";
	
	public final static String HOME_DIALYSIS_ENDPOINT = "http://rpcs.herokuapp.com/";
	public final static String POST_BIOMETRIC_HD = "api/v1/Biometrics";
}
