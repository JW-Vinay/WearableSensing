package com.wearables;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class CuraApp extends Application
{
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "UBBIKb8y8RqzSsoXXJS6iCO7D2Idb1HNk2XUcmA0", "PgraisP0vUs9pV3dIUGWqb8PRhEPh2UIgLRPJz9t");
		ParseInstallation.getCurrentInstallation().saveInBackground();
//		PushService.
	}
}
