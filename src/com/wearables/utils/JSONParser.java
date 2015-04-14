package com.wearables.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.wearables.networking.NetworkConstants;

public class JSONParser {
	private JSONObject obj;
	// private SharedPreferences sharedPref;
	private Editor ed;
	private Context mContext;

	public JSONParser(Context context) throws JSONException {
		this.mContext = context;
		// this.sharedPref = SharedPrefs.getInstance(context);
		// this.ed = this.sharedPref.edit();
	}

	public void parseResponse(String response) {
		try {
			System.out.println("Parse resposne");
			System.out.println(response);
			JSONObject jObject = new JSONObject(response);
			SharedPrefs sp = SharedPrefs.getInstance(this.mContext);
			if (jObject != null) {
				
				String accessToken = (!jObject.isNull("AccessToken")) ? jObject
						.getString("AccessToken") : "";
				sp.setParameters(NetworkConstants.ACCESS_TOKEN, accessToken);
				sp.setLongParameters(NetworkConstants.TIMESTAMP, System.currentTimeMillis());
				String userID = (!jObject.isNull("UserID")) ? jObject
						.getString("UserID") : "";
				sp.setParameters(NetworkConstants.USER_ID, userID);
				String refreshToken = (!jObject.isNull("RefreshToken")) ? jObject
						.getString("RefreshToken") : "";
				//System.out.println(refreshToken);
				sp.setParameters(NetworkConstants.REFRESH_TOKEN, refreshToken);
				System.out.println("Refresh TOKEN");
				System.out.println(SharedPrefs.getInstance(this.mContext).getParameters(NetworkConstants.REFRESH_TOKEN));
//				String responseType = (!jObject.isNull("ResponseType")) ? jObject
//						.getString("ResponseType") : "";
//				sp.setParameters(NetworkConstants.RESPONSE_TYPE, responseType);
			}
		} catch (JSONException e) {

		}
	}

	public void parseSP02(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject jObject = new JSONObject(response);
			if (jObject != null) {
				//TODO: write JSON parser
				System.out.println(jObject.toString());
			}
		} catch (JSONException e) {

		}
	}

	public void parseBP(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject jObject = new JSONObject(response);
			if (jObject != null) {
				// TODE: write JSON parser
				System.out.println(jObject.toString());
			}
		} catch (JSONException e) {

		}
	}

	public void parseRefreshToken(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject jObject = new JSONObject(response);
			SharedPrefs sp = SharedPrefs.getInstance(this.mContext);
			if (jObject != null) {
				System.out.println(jObject.toString());
				String accessToken = (!jObject.isNull("AccessToken")) ? jObject
						.getString("AccessToken") : "";
				sp.setParameters(NetworkConstants.ACCESS_TOKEN, accessToken);

				String refreshToken = (!jObject.isNull("RefreshToken")) ? jObject
						.getString("RefreshToken") : "";
				//System.out.println(refreshToken);
				sp.setParameters(NetworkConstants.REFRESH_TOKEN, refreshToken);
				sp.setLongParameters(NetworkConstants.TIMESTAMP, System.currentTimeMillis());
				
			}
		} catch (JSONException e) {

		}
	}
}
