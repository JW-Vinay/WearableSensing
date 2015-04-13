package com.wearables.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.wearables.utils.LogUtils;

import android.os.Parcel;
import android.os.Parcelable;
import java.nio.ByteBuffer;

public class BiometricBreathingModel implements Parcelable {
	
	private final String TAG = getClass().getSimpleName();
	private byte[] mBreathingSample;
	private long mTimeStampRecorded;
	
	public byte[] shortToByte(short[] short_in)
	{
		int index;
		int short_len = short_in.length;
		ByteBuffer bb = ByteBuffer.allocate(short_len*2);
		for(index=0; index<short_len; index++)
			bb.putShort(short_in[index]);    
		return bb.array();       
	}
	
	public short[] byteToShort(byte[] byte_in) 
	{
		int byte_len = byte_in.length;
		ByteBuffer bb = ByteBuffer.allocate(byte_len);
		bb.get(byte_in);    
		return bb.asShortBuffer().array(); 
	}
	
	public static final Parcelable.Creator<BiometricBreathingModel> CREATOR = new Parcelable.Creator<BiometricBreathingModel>() {
		public BiometricBreathingModel createFromParcel(Parcel in) {
			return new BiometricBreathingModel(in);
		}

		public BiometricBreathingModel[] newArray(int size) {
			return new BiometricBreathingModel[size];
		}
	};
	
	/**
	 * 
	 * @param in
	 */
	public BiometricBreathingModel(Parcel in)
	{
		readFromParcel(in);
	}
	
	public BiometricBreathingModel(short[] mBreathingSample, long mTimeStampRecorded) {
		super();
		this.mBreathingSample = shortToByte(mBreathingSample);
		this.mTimeStampRecorded = mTimeStampRecorded;
	}

	public short[] getmBreathingSample() {
		return byteToShort(mBreathingSample);
	}

	public void setmBreathingSample(short[] mBreathingSample) {
		this.mBreathingSample = shortToByte(mBreathingSample);
	}

	public long getmTimeStampRecorded() {
		return mTimeStampRecorded;
	}

	public void setmTimeStampRecorded(long mTimeStampRecorded) {
		this.mTimeStampRecorded = mTimeStampRecorded;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	private void readFromParcel(Parcel in)
	{
			in.readByteArray(mBreathingSample);		
			mTimeStampRecorded = in.readLong();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeByteArray(mBreathingSample);	
		dest.writeLong(mTimeStampRecorded);

	}
	
	/**
	 * Get json object for the model
	 * @return
	 */
	public JSONObject getJSON()
	{
		try
		{
			JSONObject object = new JSONObject();
			object.put("breathing_sample", byteToShort(mBreathingSample));
//			object.put("time_recorded", mTimeStampRecorded);
			
			return object;
		}
		catch(JSONException e)
		{
			LogUtils.LOGE(TAG, "" + e.getMessage());
		}
		
		return null;
	}

}

