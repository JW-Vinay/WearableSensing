package com.wearables.utils;

public class Constants {

	public static final String INTENT_TASK_ACTION = "task_action";
	public static final String INTENT_DEV = "bluetooth_device";
	public static final String INTENT_SUMMARY = "summary";
	public static final String INTENT_SUMMARY_MODEL = "summary_model";
	public static final String INTENT_BREATHING = "breathing";
	public static final String INTENT_BREATHING_MODEL = "breathing_model";	
	public static final String INTENT_ECG = "ecg";
	public static final String INTENT_ECG_MODEL = "ecg_model";	
	
	public static enum SERVICE_ACTIONS
	{
		START_SERVICE, PAIR_DEVICE, CONNECT_DEVICE,
	}
}
