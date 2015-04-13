package com.wearables.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.wearables.utils.LogUtils;

import android.os.Parcel;
import android.os.Parcelable;

import java.nio.ByteBuffer;


public class BiometricECGModel implements Parcelable {

	private final String TAG = getClass().getSimpleName();
	private byte[] mECGSample;
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
	
	public static final Parcelable.Creator<BiometricECGModel> CREATOR = new Parcelable.Creator<BiometricECGModel>() {
		public BiometricECGModel createFromParcel(Parcel in) {
			return new BiometricECGModel(in);
		}

		public BiometricECGModel[] newArray(int size) {
			return new BiometricECGModel[size];
		}
	};
	
	
	/**
	 * 
	 * @param in
	 */
	public BiometricECGModel(Parcel in)
	{
		readFromParcel(in);
	}
	
	public BiometricECGModel(short[] mECGSample, long mTimeStampRecorded) {
		super();
		this.mECGSample = shortToByte(mECGSample);
		this.mTimeStampRecorded = mTimeStampRecorded;
	}

	public short[] getmECGSample() {
		return byteToShort(mECGSample);
	}

	public void setmECGSample(short[] mECGSample) {
		this.mECGSample = shortToByte(mECGSample);
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
			in.readByteArray(mECGSample);		
			mTimeStampRecorded = in.readLong();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {	
		dest.writeByteArray(mECGSample);	
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
			object.put("ecg_sample", byteToShort(mECGSample));
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
