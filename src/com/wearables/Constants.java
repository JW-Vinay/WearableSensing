package com.wearables;

public class Constants {

	public static final String INTENT_TASK_ACTION = "task_action";
	public static final String INTENT_DEV = "bluetooth_device";
	
	public static final String SHARED_FILE = "wearablesconf";
	public static final int  EXPIRY_TIME = 10 * 60 * 1000;
	
	public static enum SERVICE_ACTIONS
	{
		START_SERVICE, PAIR_DEVICE, CONNECT_DEVICE,
	}
}
