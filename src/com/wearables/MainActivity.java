package com.wearables;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wearables.Constants.SERVICE_ACTIONS;
import com.wearables.networking.NetworkUtils;

public class MainActivity extends Activity {

	private Button miHealthBtn;
	
	private TextView mBreathing, mHeart,mTemp, mPosture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		mTemp = (TextView) findViewById(R.id.tempTextView);
		mBreathing = (TextView) findViewById(R.id.breathTextView);
//		mPosture = (TextView) findViewById(R.id.postureTextView);
//		mHeart = (TextView) findViewById(R.id.respTextView);
		
		miHealthBtn = (Button) findViewById(R.id.ihealthBtn);
		miHealthBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
				
				Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
				String url = NetworkUtils.generateUrl("", NetworkUtils.getAuthorizationParams()); //TODO: Change here
				intent.putExtra("url", url);
				startActivity(intent);
				
			}
		});
		
		Intent intent = new Intent(this, DataCollectService.class);
		intent.putExtra(Constants.INTENT_TASK_ACTION, SERVICE_ACTIONS.START_SERVICE);
		startService(intent);
	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		IntentFilter filter = new IntentFilter("com.wearable.ui");
		registerReceiver(new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				
				mBreathing.setText(intent.getExtras().getString("summary"));
				
			}
		}, filter);
    	//IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		//registerReceiver(mReceiver, filter);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) {
//		case REQUEST_ENABLE_BT:
//			if (resultCode == RESULT_OK) {
//
//			} else {
//				Toast.makeText(this, "Disabled", Toast.LENGTH_SHORT).show();
//			}
//			break;
		case 300:
			//discoverable now
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
