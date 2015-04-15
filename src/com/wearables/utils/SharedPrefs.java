package com.wearables.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.wearables.utils.Constants;

public class SharedPrefs {
	private static SharedPrefs instance = null;
	private SharedPreferences mPrefs;
	private Editor editor;
	private SharedPrefs(Context context){
		
		mPrefs = context.getSharedPreferences(Constants.SHARED_FILE, 0);
		this.editor = mPrefs.edit();
//		mPrefs.edit();
	}
	
	public static SharedPrefs getInstance(Context context){
		if(instance == null){
			instance = new SharedPrefs(context) ;
		}
		return instance;
	}
	
	public void setLongParameters(String key, long value)
	{
		this.editor.putLong(key, value);
		this.editor.commit();
	}
	
	public long getLongParameters(String key)
	{
		return mPrefs.getLong(key, 0);
	}
	
	public void setParameters(String key, String value){
		
		this.editor.putString(key, value);
		this.editor.commit();
	}
	
	public String getParameters(String key){
		return mPrefs.getString(key, "");		
	}
	
	
}
