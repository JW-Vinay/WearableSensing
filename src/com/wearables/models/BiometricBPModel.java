package com.wearables.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.wearables.utils.LogUtils;
import com.wearables.utils.Utils;

import android.os.Parcel;
import android.os.Parcelable;

public class BiometricBPModel implements Parcelable{

	private final String TAG = getClass().getSimpleName();
	
	private int mSystolic;
	private int mDystolic;
	private int mPulse;
	private long mTimeStampRecorded;
	private String mFormattedTime;
	
	public static final Parcelable.Creator<BiometricBPModel> CREATOR = new Parcelable.Creator<BiometricBPModel>() {
		public BiometricBPModel createFromParcel(Parcel in) {
			return new BiometricBPModel(in);
		}

		public BiometricBPModel[] newArray(int size) {
			return new BiometricBPModel[size];
		}
	};
	
	/**
	 * 
	 * @param in
	 */
	public BiometricBPModel(Parcel in)
	{
		readFromParcel(in);
	}
	
	public BiometricBPModel(int mSystolic, int mDystolic, 
			int mPulse, long mTimeStampRecorded) {
		super();
		this.mSystolic = mSystolic;
		this.mDystolic = mDystolic;
		this.mPulse = mPulse;
		this.mTimeStampRecorded = mTimeStampRecorded;
		this.setmFormattedTime(Utils.getFormattedTime(mTimeStampRecorded));
	}
	
	public int getmSystolic() {
		return mSystolic;
	}

	public void setmSystolic(int mSystolic) {
		this.mSystolic = mSystolic;
	}

	public int getmDystolic() {
		return mDystolic;
	}

	public void setmDystolic(int mDystolic) {
		this.mDystolic = mDystolic;
	}

	public int getmPulse() {
		return mPulse;
	}

	public void setmPulse(int mPulse) {
		this.mPulse = mPulse;
	}

	public long getmTimeStampRecorded() {
		return mTimeStampRecorded;
	}

	public void setmTimeStampRecorded(long mTimeStampRecorded) {
		this.mTimeStampRecorded = mTimeStampRecorded;
	}
	
	public String getmFormattedTime() {
		return mFormattedTime;
	}

	public void setmFormattedTime(String mFormattedTime) {
		this.mFormattedTime = mFormattedTime;
	}
	
	private void readFromParcel(Parcel in){
		mSystolic = in.readInt();
		mDystolic = in.readInt();
		mPulse = in.readInt();
		mTimeStampRecorded = in.readLong();
		mFormattedTime = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mSystolic);
		dest.writeInt(mDystolic);
		dest.writeInt(mPulse);
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
			object.put("systolic", mSystolic);
			object.put("dystolic", mDystolic);
			object.put("pulse", mPulse);
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
