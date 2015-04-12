package com.wearables.utils;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wearables.DataCollectService;
import com.wearables.utils.Constants.SERVICE_ACTIONS;

public class BluetoothPairingReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
        if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
            // Get the BluetoothDevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			if(device.getAddress().compareTo("C8:3E:99:0D:6B:EE") == 0) {
				Intent workIntent = new Intent(context, DataCollectService.class);
				workIntent.putExtra(Constants.INTENT_TASK_ACTION, SERVICE_ACTIONS.PAIR_DEVICE);
				workIntent.putExtra(Constants.INTENT_DEV, device);
				context.startService(workIntent);
			}
        }
	}
}
