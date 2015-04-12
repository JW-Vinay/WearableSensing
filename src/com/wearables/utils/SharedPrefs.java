package com.wearables.utils;

import com.wearables.Constants;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
	private static SharedPrefs instance = null;
	private static SharedPreferences sharedPref;
	private SharedPrefs(Context context){
		sharedPref = context.getSharedPreferences(Constants.SHARED_FILE, 0);
	}
	
	public static SharedPrefs getInstance(Context context){
		if(instance == null){
			instance = new SharedPrefs(context);
		}
		return instance;
	}
	
	
}
