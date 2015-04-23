package com.wearables;

import java.lang.reflect.Method;
import java.util.Set;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.wearables.models.BiometricBreathingModel;
import com.wearables.models.BiometricECGModel;
import com.wearables.models.BiometricSummaryModel;
import com.wearables.models.NetworkWrapperObject;
import com.wearables.networking.NetworkCompletionInterface;
import com.wearables.networking.NetworkConstants.REQUEST_TYPE;
import com.wearables.networking.NetworkQueue;
import com.wearables.networking.NetworkUtils;
import com.wearables.utils.Constants;
import com.wearables.utils.Constants.SERVICE_ACTIONS;
import com.wearables.utils.LogUtils;
import com.wearables.zephyr.BTClient;
import com.wearables.zephyr.ConnectListenerImpl;
import com.wearables.zephyr.ConnectedListener;
import com.wearables.zephyr.ZephyrProtocol;

/**
 * This service pulls RSS content from a web site URL contained in the incoming Intent (see
 * onHandleIntent()). As it runs, it broadcasts its status using LocalBroadcastManager; any
 * component that wants to see the status should implement a subclass of BroadcastReceiver and
 * register to receive broadcast Intents with category = CATEGORY_DEFAULT and action
 * Constants.BROADCAST_ACTION.
 *
 */
public class DataCollectService extends IntentService implements NetworkCompletionInterface{
    // Used to write to the system log from this class.
//    public static final String LOG_TAG = "DataCollectService";
	private final String TAG = getClass().getSimpleName();
    
    //Zephyr BH3
//	private final int GEN_PACKET = 1200;
	private final int ECG_PACKET = 1202;
	private final int BREATH_PACKET = 1204;
//	private final int R_to_R_PACKET = 1206;
//	private final int ACCELEROMETER_PACKET = 1208;
//	private final int SERIAL_NUM_PACKET = 1210;
	private final int SUMMARY_DATA_PACKET =1212;
//	private final int EVENT_DATA_PACKET =1214;
	public byte[] DataBytes;
	
	
//	private final int REQUEST_ENABLE_BT = 100;
	
//	private BiometricSummaryModel mBioMetricModel;
	private 		BluetoothAdapter mBluetoothAdapter;
	BTClient _bt;
	ZephyrProtocol _protocol;
	ConnectedListener<BTClient> _listener;
	private Handler mHandler = new Handler();
	private NetworkQueue mNetworkQueue;
	
	//Zephyr BH3
	
    /**
     * An IntentService must always have a constructor that calls the super constructor. The
     * string supplied to the super constructor is used to give a name to the IntentService's
     * background thread.
     */
    public DataCollectService() {

        super("DataCollectService");
        mNetworkQueue = NetworkQueue.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	// TODO Auto-generated method stub
     super.onStartCommand(intent, flags, startId);
     mHandler.removeCallbacks(null);
     mHandler.postDelayed(mPostRunnable, Constants.INTERVAL_MILLIS);
     return START_STICKY;
    }
    /**
     * In an IntentService, onHandleIntent is run on a background thread.  As it
     * runs, it broadcasts its current status using the LocalBroadcastManager.
     * @param workIntent The Intent that starts the IntentService. This Intent contains the
     * URL of the web site from which the RSS parser gets data.
     */
    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets a URL to read from the incoming Intent's "data" value
//        String localUrlString = workIntent.getDataString();
    	SERVICE_ACTIONS action = (SERVICE_ACTIONS) workIntent.getSerializableExtra(Constants.INTENT_TASK_ACTION);
    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Not Supported", Toast.LENGTH_SHORT).show();
			return;
		} 
	
    	if(action == SERVICE_ACTIONS.START_SERVICE)
    	{
    		 //Zephyr BH3
//    		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//    		if (mBluetoothAdapter == null) {
//    			Toast.makeText(this, "Not Supported", Toast.LENGTH_SHORT).show();
//    		} 
//    		else {
    			if (!mBluetoothAdapter.isEnabled()) {
    				Intent discoverableIntent = new Intent(
    						BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
    				discoverableIntent.putExtra(
    						BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
    				discoverableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    				startActivity(discoverableIntent);
    			}
    			else {
    				queryPairedDevices();
    			}
//    		}
    	}
    	else if(action == SERVICE_ACTIONS.PAIR_DEVICE) { 
    		mBluetoothAdapter.cancelDiscovery();
    		BluetoothDevice device = workIntent.getParcelableExtra(Constants.INTENT_DEV);
    		pairDevice(device);
        }
    	else if(action == SERVICE_ACTIONS.CONNECT_DEVICE)
    		connectDevice();
    	else if(action == SERVICE_ACTIONS.DISCONNECT_DEVICE)
    	{
    		_bt.removeConnectedEventListener(_listener);
			/*Close the communication with the device & throw an exception if failure*/
			_bt.Close();
    	}
    		
       
    }
    
    private Runnable mPostRunnable = new Runnable() {
		
		@Override
		public void run() {
			NetworkWrapperObject object = mNetworkQueue.getItem();
			boolean isRunning = mNetworkQueue.ismIsTaskRunning();
			if(object != null && !isRunning)
			{
//				System.out.println("API INvoked");
				REQUEST_TYPE type = object.getmType();
				switch(type)
				{
				 case POST_BIOMETRIC_ZEPHYR:
					mNetworkQueue.setmIsTaskRunning(true); 
					Object postObject = object.getmObject();
					if(postObject instanceof BiometricSummaryModel)
						NetworkUtils.postBiometricData(DataCollectService.this, (BiometricSummaryModel)object.getmObject(), DataCollectService.this);
					else if(postObject instanceof BiometricECGModel)
						NetworkUtils.postBiometricData(DataCollectService.this, (BiometricECGModel)object.getmObject(), DataCollectService.this);
					else if(postObject instanceof BiometricBreathingModel)	
						NetworkUtils.postBiometricData(DataCollectService.this, (BiometricBreathingModel)object.getmObject(), DataCollectService.this);
					break;
				 case POST_PIP:
					 mNetworkQueue.setmIsTaskRunning(true);
					 break;
				default:
						 break;
				}
			}
			mHandler.removeCallbacks(null);
			mHandler.postDelayed(mPostRunnable, Constants.INTERVAL_MILLIS);

		}
	};

	final Handler handler = new Handler() {
	   	public void handleMessage(Message msg) {
	   		//TextView tv;
	   		Intent intent = new Intent();
	   		switch (msg.what)
	   		{
		  		case BREATH_PACKET:
		  			String breathText = msg.getData().getString(Constants.INTENT_BREATHING);
//		       		System.out.println("" + "test" + breathText);
		       		BiometricBreathingModel model_breath = msg.getData().getParcelable(Constants.INTENT_BREATHING_MODEL);
//		       		NetworkUtils.postBiometricData(DataCollectService.this, model_breath);
		       		mNetworkQueue.addToQueue(model_breath, REQUEST_TYPE.POST_BIOMETRIC_ZEPHYR);
		       		intent.setAction("com.wearable.ui");
		       		intent.putExtra("breathing", breathText);
		       		sendBroadcast(intent);
//		  			tv = (TextView)findViewById(R.id.genText);
//		  			if (tv != null) tv.setText(genText);
		  			break;
		   		case ECG_PACKET:
		  			String ecgText = msg.getData().getString(Constants.INTENT_ECG);
//		       		System.out.println("" + "test" + ecgText);
		       		BiometricECGModel model_ecg = msg.getData().getParcelable(Constants.INTENT_ECG_MODEL);
//		       		NetworkUtils.postBiometricData(DataCollectService.this, model_ecg);
		       		mNetworkQueue.addToQueue(model_ecg, REQUEST_TYPE.POST_BIOMETRIC_ZEPHYR);
		       		intent.setAction("com.wearable.ui");
		       		intent.putExtra("ECG", ecgText);
		       		sendBroadcast(intent);	   			
//		   			tv = (TextView)findViewById(R.id.ecgText);
//		   			String ecgText = msg.getData().getString("ecgText");if (tv != null) tv.setText(ecgText);
		   			break;
		   		case SUMMARY_DATA_PACKET:
		       		String summaryText = msg.getData().getString(Constants.INTENT_SUMMARY);
//		       		System.out.println("" + "test" + summaryText);
		       		BiometricSummaryModel model_summary = msg.getData().getParcelable(Constants.INTENT_SUMMARY_MODEL);
		       		mNetworkQueue.addToQueue(model_summary, REQUEST_TYPE.POST_BIOMETRIC_ZEPHYR);
//		       		mBioMetricModel = 
//		       		NetworkUtils.postBiometricData(DataCollectService.this, model_summary);       		
		       		intent.setAction("com.wearable.ui");
		       		intent.putExtra("summary", summaryText);
		       		sendBroadcast(intent);
//		       		tv = (TextView)findViewById(R.id.SummaryDataText);
//		       		if (tv != null) tv.setText(SummaryText);
		   			break;
		   	}
	   	}
	};
	
	
	private void queryPairedDevices()
	{
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		BluetoothDevice dev = null;
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		    	if("C8:3E:99:0D:6B:EE".compareTo(device.getAddress()) == 0)
		    	{
		    		dev = device;
//		    		connectDevice(device);
		    		//got it break;
		    		break;
		    	}
	           // Add the name and address to an array adapter to show in a ListView
//			        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
			}
		}
			
		if(dev != null)
		{
			if(_bt == null || !_bt.IsConnected())
				connectDevice();
			else
				_bt.start();
		}
		else
			mBluetoothAdapter.startDiscovery();
	}
	
//	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
//	    public void onReceive(Context context, Intent intent) {
//	        String action = intent.getAction();
//	        // When discovery finds a device
//	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//	            // Get the BluetoothDevice object from the Intent
//	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//	            if(device.getAddress().compareTo("C8:3E:99:0D:6B:EE") == 0)
//	            {
////	            	mBluetoothAdapter.cancelDiscovery();
////	            	connectDevice(device);
//	            	pairDevice(device);
//	            }
//	            // Add the name and address to an array adapter to show in a ListView
////	            mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//	        }
//	    }
//	};
	
	private void pairDevice(BluetoothDevice device)
	{
		try {
	        LogUtils.LOGI(TAG, "Start Pairing...");
	        Method m = device.getClass().getMethod("createBond", (Class[]) null);
	        m.invoke(device, (Object[]) null);
	        LogUtils.LOGI(TAG, "Pairing finished.");
	        connectDevice();
	    } 
		catch (Exception e) {
	        LogUtils.LOGE(TAG, ""+e.getMessage());
	        e.printStackTrace();
		}
	}
		
	private void connectDevice()
	{
		_bt = new BTClient(mBluetoothAdapter, "C8:3E:99:0D:6B:EE");
		/*Instantiates a new object implementing the connected Listener interface and
		/passes it the handler object*/
		_listener = new ConnectListenerImpl(handler,DataBytes);
		
		/*Adds the ConnectListenerImpl to the BTClients eventsubscriber list to respond to OnConnected() method*/
		_bt.addConnectedEventListener(_listener);
		/*Kick off the thread to launch its activity*/
		if(_bt.IsConnected())
		{
			_bt.start();
		}
	}

	@Override
	public void onTaskComplete() {
		mNetworkQueue.setmIsTaskRunning(false);
		mHandler.removeCallbacks(null);
		mHandler.postDelayed(mPostRunnable, Constants.INTERVAL_MILLIS);
	}
}

