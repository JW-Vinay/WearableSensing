package com.wearables.networking;

import java.util.ArrayList;
import java.util.List;

import com.wearables.models.NetworkWrapperObject;
import com.wearables.networking.NetworkConstants.REQUEST_TYPE;

public class NetworkQueue {

	private List<NetworkWrapperObject> mQueue;
	private static NetworkQueue instance;
	private boolean mIsTaskRunning = false;
	
	private NetworkQueue()
	{
		
	}
	
	public static NetworkQueue getInstance()
	{
		if(instance == null)
			instance = new NetworkQueue();
		
		return instance;
	}
	
	public boolean isQueueEmpty()
	{
		if(mQueue == null || mQueue.isEmpty())
			return true;
		
		return false;
	}
	
	
	public void addToQueue(Object obj, REQUEST_TYPE type)
	{
		if(isQueueEmpty())
			mQueue = new ArrayList<NetworkWrapperObject>();
		
		NetworkWrapperObject wrapperObject = new NetworkWrapperObject(obj, type);
		mQueue.add(wrapperObject);
	}
	
	public int getSize()
	{
		return  (!isQueueEmpty())?mQueue.size() : 0;
	}
	
	public NetworkWrapperObject getItem()
	{
		NetworkWrapperObject object = null;
		if(!isQueueEmpty())
			object = mQueue.remove(0);
		return object; 
	}
	
	public NetworkWrapperObject remove(Object obj, REQUEST_TYPE type)
	{
		if(!isQueueEmpty())
		{
			NetworkWrapperObject object = new NetworkWrapperObject(obj, type);
			mQueue.remove(object);
			return object;
		}
		return null;
	}
	
	
	public List<NetworkWrapperObject> getmQueue() {
		return mQueue;
	}

	public void setmQueue(List<NetworkWrapperObject> mQueue) {
		this.mQueue = mQueue;
	}

	public boolean ismIsTaskRunning() {
		return mIsTaskRunning;
	}

	public void setmIsTaskRunning(boolean mIsTaskRunning) {
		this.mIsTaskRunning = mIsTaskRunning;
	}
	
	
}
