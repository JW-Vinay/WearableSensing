package com.wearables.utils;

public class Constants {

	public static final String INTENT_TASK_ACTION = "task_action";
	public static final String INTENT_DEV = "bluetooth_device";
	public static final String INTENT_SUMMARY = "summary";
	public static final String INTENT_SUMMARY_MODEL = "summary_model";
	
	public static enum SERVICE_ACTIONS
	{
		START_SERVICE, PAIR_DEVICE, CONNECT_DEVICE,
	}
	
	public static final long INTERVAL_MILLIS = 1 * 60 * 1000;
//	public static final long INITIAL_START_TIME = 30 * 1000;
}
