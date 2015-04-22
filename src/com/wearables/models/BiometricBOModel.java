package com.wearables.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.wearables.utils.LogUtils;
import com.wearables.utils.Utils;

import android.os.Parcel;
import android.os.Parcelable;

public class BiometricBOModel implements Parcelable{
	
	private final String TAG = getClass().getSimpleName();

	private int mBloodOxygen;
	private long mTimeStampRecorded;
	private String mFormattedTime;
	
	public static final Parcelable.Creator<BiometricBOModel> CREATOR = new Parcelable.Creator<BiometricBOModel>() {
		public BiometricBOModel createFromParcel(Parcel in) {
			return new BiometricBOModel(in);
		}

		public BiometricBOModel[] newArray(int size) {
			return new BiometricBOModel[size];
		}
	};
	
	/**
	 * 
	 * @param in
	 */
	public BiometricBOModel(Parcel in)
	{
		readFromParcel(in);
	}
	

	public BiometricBOModel(int mBloodOxygen, long mTimeStampRecorded) {
		super();
		this.mBloodOxygen = mBloodOxygen;
		this.mTimeStampRecorded = mTimeStampRecorded;
		this.setmFormattedTime(Utils.getFormattedTime(mTimeStampRecorded));	
	}
	
	public int getmBloodOxygen() {
		return mBloodOxygen;
	}

	public void setmBloodOxygen(int mBloodOxygen) {
		this.mBloodOxygen = mBloodOxygen;
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
		mBloodOxygen = in.readInt();
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
		dest.writeInt(mBloodOxygen);
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
			object.put("blood_oxygen", String.valueOf(mBloodOxygen));
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
