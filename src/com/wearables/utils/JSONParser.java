package com.wearables.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class JSONParser {
	private JSONObject obj;
	private SharedPrefs sharedPref;
	public JSONParser(Context context, String json) throws JSONException{
		this.obj = new JSONObject(json);
		this.sharedPref = SharedPrefs.getInstance(context);
	}
	
	public void setParameters(){
		
	}
}
