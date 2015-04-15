package com.wearables.models;

import java.nio.ByteBuffer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.wearables.utils.LogUtils;
import com.wearables.utils.Utils;


public class BiometricECGModel implements Parcelable {

	private final String TAG = getClass().getSimpleName();
	private byte[] mECGSample;
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
	
	public BiometricECGModel(short[] mECGSample, long mTimeStampRecorded, int mNoSamples, int seqNo) {
		super();
		this.mECGSample = shortToByte(mECGSample);
		this.mTimeStampRecorded = mTimeStampRecorded;
		this.setmNoSamples(mNoSamples);
		this.setmSeqNo(seqNo);
		mFormattedTime = Utils.getFormattedTime(mTimeStampRecorded);
	}

	public String getmFormattedTime() {
		return mFormattedTime;
	}

	public void setmFormattedTime(String mFormattedTime) {
		this.mFormattedTime = mFormattedTime;
	}

	
	public byte[] getmECGSample() {
//		return byteToShort(mECGSample);
		return mECGSample;
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
			in.readByteArray(mECGSample);		
			mTimeStampRecorded = in.readLong();
			mSeqNo = in.readInt();
			mNoSamples = in.readInt();
			mFormattedTime = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {	
		dest.writeByteArray(mECGSample);	
		dest.writeLong(mTimeStampRecorded);
		dest.writeInt(mSeqNo);
		dest.writeInt(mNoSamples);
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
			JSONObject object = new JSONObject();
//			object.put("ecg_sample", byteToShort(mECGSample));
			object.put("time_recorded", mFormattedTime);
			object.put("samples_per_packet ", mNoSamples);
			object.put("sequence_number", mSeqNo);
			object.put("record_type", "ecg");
			JSONArray array = new JSONArray();
			for(byte a : mECGSample)
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
