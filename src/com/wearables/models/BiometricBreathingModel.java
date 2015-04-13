package com.wearables.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wearables.utils.LogUtils;
import com.wearables.utils.Utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.nio.ByteBuffer;

public class BiometricBreathingModel implements Parcelable {
	
	private final String TAG = getClass().getSimpleName();
	private byte[] mBreathingSample;
	private long mTimeStampRecorded;
	private int mNoSamples;
	private int mSeqNo;
	private String mFormattedTime;
	
	public byte[] shortToByte(short[] short_in)
	{
		int index;
		int short_len = short_in.length;
		ByteBuffer bb = ByteBuffer.allocate(short_len*2);
		for(index=0; index<short_len; index++)
			bb.putShort(short_in[index]);    
		return bb.array();       
	}
	
//	public short[] byteToShort(byte[] byte_in) 
//	{
//		int byte_len = byte_in.length;
//		ByteBuffer bb = ByteBuffer.allocate(byte_len);
//		bb.get(byte_in);    
//		return bb.asShortBuffer().array(); 
//	}
	
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
	
	public BiometricBreathingModel(short[] mBreathingSample, long mTimeStampRecorded, int mNoSamples, int mSeqNo) {
		super();
		this.mBreathingSample = shortToByte(mBreathingSample);
		this.mTimeStampRecorded = mTimeStampRecorded;
		this.mSeqNo = mSeqNo;
		this.mNoSamples = mNoSamples;
		mFormattedTime = Utils.getFormattedTime(mTimeStampRecorded);
	}

	public byte[] getmBreathingSample() {
//		return byteToShort(mBreathingSample);
		return mBreathingSample;
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
	
	public int getmSeqNo() {
		return mSeqNo;
	}

	public void setmSeqNo(int mSeqNo) {
		this.mSeqNo = mSeqNo;
	}

	public int getmNoSamples() {
		return mNoSamples;
	}

	public void setmNoSamples(int mNoSamples) {
		this.mNoSamples = mNoSamples;
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
			mSeqNo = in.readInt();
			mNoSamples = in.readInt();
			mFormattedTime = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeByteArray(mBreathingSample);	
		dest.writeLong(mTimeStampRecorded);
		dest.writeInt(mSeqNo);
		dest.writeInt(mNoSamples);
		dest.writeString(mFormattedTime);

	}
	
	public String getmFormattedTime() {
		return mFormattedTime;
	}

	public void setmFormattedTime(String mFormattedTime) {
		this.mFormattedTime = mFormattedTime;
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
//			object.put("breathing_sample", byteToShort(mBreathingSample));
			object.put("time_recorded", mFormattedTime);
			object.put("samples_per_packet ", mNoSamples);
			object.put("sequence_number", mSeqNo);
			object.put("record_type", "breathing");
			JSONArray array = new JSONArray();
			for(byte a : mBreathingSample)
				array.put(a);
			object.put("samples", array);
			
			return object;
		}
		catch(JSONException e)
		{
			LogUtils.LOGE(TAG, "" + e.getMessage());
		}
		
		return null;
	}

}

