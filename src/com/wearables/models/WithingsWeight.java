package com.wearables.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.wearables.utils.LogUtils;
import com.wearables.utils.Utils;

import android.os.Parcel;
import android.os.Parcelable;

public class WithingsWeight implements Parcelable{
	
	private final String TAG = getClass().getSimpleName();

	private int mWeight;
	private long mTimeStampRecorded;
	private String mFormattedTime;
	
	public static final Parcelable.Creator<WithingsWeight> CREATOR = new Parcelable.Creator<WithingsWeight>() {
		public WithingsWeight createFromParcel(Parcel in) {
			return new WithingsWeight(in);
		}

		public WithingsWeight[] newArray(int size) {
			return new WithingsWeight[size];
		}
	};
	
	/**
	 * 
	 * @param in
	 */
	public WithingsWeight(Parcel in)
	{
		readFromParcel(in);
	}
	

	public WithingsWeight(int mWeight, long mTimeStampRecorded) {
		super();
		this.mWeight = mWeight;
		this.mTimeStampRecorded = mTimeStampRecorded;
		this.setmFormattedTime(Utils.getFormattedTime(mTimeStampRecorded));	
	}
	
	public int getmWeight() {
		return mWeight;
	}

	public void setmWeight(int mWeight) {
		this.mWeight = mWeight;
	}

	public long getmTimeRecorded() {
		return mTimeStampRecorded;
	}

	public void setmTimeRecorded(long mTimeStampRecorded) {
		this.mTimeStampRecorded = mTimeStampRecorded;
	}

	public String getmFormattedTime() {
		return mFormattedTime;
	}

	public void setmFormattedTime(String mFormattedTime) {
		this.mFormattedTime = mFormattedTime;
	}

	private void readFromParcel(Parcel in) {
		mWeight = in.readInt();
		mTimeStampRecorded = in.readLong();
		mFormattedTime = in.readString();
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mWeight);
		dest.writeLong(mTimeStampRecorded);
		dest.writeString(mFormattedTime);
	}
	
	/**
	 * Get json object for the model
	 * @return
	 */
	public JSONObject getJSON()
	{
		try
		{
			//TODO: change names of params according to API
			JSONObject object = new JSONObject();
			object.put("weight", String.valueOf(mWeight));
			object.put("time_recorded", mFormattedTime);		
			
			return object;
		}
		catch(JSONException e)
		{
			LogUtils.LOGE(TAG, "" + e.getMessage());
		}
		
		return null;
	}

}
