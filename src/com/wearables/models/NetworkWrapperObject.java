package com.wearables.models;

import com.wearables.networking.NetworkConstants.REQUEST_TYPE;

public class NetworkWrapperObject {

	private Object mObject;
	private REQUEST_TYPE mType;
	
	
	/**
	 * 
	 * @param mObject
	 * @param mType
	 */
	public NetworkWrapperObject(Object mObject, REQUEST_TYPE mType) {
		super();
		this.mObject = mObject;
		this.mType = mType;
	}
	
	public Object getmObject() {
		return mObject;
	}
	public void setmObject(Object mObject) {
		this.mObject = mObject;
	}
	public REQUEST_TYPE getmType() {
		return mType;
	}
	public void setmType(REQUEST_TYPE mType) {
		this.mType = mType;
	}
	
	
}
