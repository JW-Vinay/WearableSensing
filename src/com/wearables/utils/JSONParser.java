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
			JSONObject jObject = new JSONObject(response);
			SharedPrefs sp = SharedPrefs.getInstance(this.mContext);
			if (jObject != null) {
				String accessToken = (!jObject.isNull("AccessToken")) ? jObject
						.getString("AccessToken") : "";
				sp.setParameters(NetworkConstants.ACCESS_TOKEN, accessToken);
				String userID = (!jObject.isNull("UserID")) ? jObject
						.getString("UserID") : "";
				sp.setParameters(NetworkConstants.USER_ID, userID);
				String refreshToken = (!jObject.isNull("RefreshToken")) ? jObject
						.getString("RefreshToken") : "";
				sp.setParameters(NetworkConstants.REFRESH_TOKEN, refreshToken);
			}
		} catch (JSONException e) {

		}
	}

	public void parseSP02(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject jObject = new JSONObject(response);
			if (jObject != null) {
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
				System.out.println(jObject.toString());
			}
		} catch (JSONException e) {

		}
	}
}
