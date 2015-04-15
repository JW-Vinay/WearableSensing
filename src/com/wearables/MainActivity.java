package com.wearables;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wearables.networking.NetworkConstants;
import com.wearables.networking.NetworkConstants.METHOD_TYPE;
import com.wearables.networking.NetworkConstants.REQUEST_TYPE;
import com.wearables.networking.NetworkUtils;
import com.wearables.networking.NetworkingTask;
import com.wearables.utils.Constants;
import com.wearables.utils.SharedPrefs;


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
				
				String a = SharedPrefs.getInstance(MainActivity.this).getParameters(NetworkConstants.ACCESS_TOKEN);
				// checks if access token exists or not
				if(TextUtils.isEmpty(SharedPrefs.getInstance(MainActivity.this).getParameters(NetworkConstants.ACCESS_TOKEN)))
				{
					Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
					String url = NetworkUtils.generateUrl(NetworkConstants.USER_AUTH_URL, NetworkUtils.getAuthorizationParams()); //TODO: Change here
					intent.putExtra("url", url);
					startActivityForResult(intent, 100);
				}
				else
				{
					int diff = (int)(System.currentTimeMillis() - SharedPrefs.getInstance(MainActivity.this).getLongParameters(NetworkConstants.TIMESTAMP));
					
					/*
					 * If the time is greater than access token expiry and call refresh the access token 
					 */
					if(diff > Constants.EXPIRY_TIME)
					{
						String url = NetworkUtils.generateUrl(NetworkConstants.USER_AUTH_URL,
			     				NetworkUtils.getRefreshTokenParams(MainActivity.this));
			     		System.out.println("URL for refresh "+url);
			     		new NetworkingTask(url, true, METHOD_TYPE.GET,
			     				REQUEST_TYPE.REFRESH_TOKEN, MainActivity.this)
			     				.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					}
					else
					{
						String accessToken = SharedPrefs.getInstance(MainActivity.this).getParameters(NetworkConstants.ACCESS_TOKEN);
						String userID = SharedPrefs.getInstance(MainActivity.this).getParameters(NetworkConstants.USER_ID);
						String SPO2_Url = NetworkUtils.generateUrl(NetworkConstants.GET_BIODATA_URL + 
								"/" + userID + "/spo2.json" , 
								NetworkUtils.getDataParams(accessToken, NetworkConstants.SPO2_SV));
				
						String BP_Url = NetworkUtils.generateUrl(NetworkConstants.GET_BIODATA_URL + 
								"/" + userID + "/bp.json" , 
								NetworkUtils.getDataParams(accessToken, NetworkConstants.BP_SV));
						new NetworkingTask(SPO2_Url, true, METHOD_TYPE.GET, REQUEST_TYPE.SP02, MainActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
						new NetworkingTask(BP_Url, true, METHOD_TYPE.GET, REQUEST_TYPE.BP, MainActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					}
				}
				
				
						
			}
		});
		
//		Intent intent = new Intent(this, DataCollectService.class);
//		intent.putExtra(Constants.INTENT_TASK_ACTIONs, SERVICE_ACTIONS.START_SERVICE);
//		startService(intent);
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
		switch (requestCode) {
//		case REQUEST_ENABLE_BT:
//			if (resultCode == RESULT_OK) {
//
//			} else {
//				Toast.makeText(this, "Disabled", Toast.LENGTH_SHORT).show();
//			}
//			break;
		case 100:
			String code = data.getStringExtra("code");
			String url = NetworkUtils.generateUrl(
					NetworkConstants.USER_AUTH_URL,
					NetworkUtils.getAccessTokenParams(code));
			new NetworkingTask(url, true, METHOD_TYPE.GET,
					REQUEST_TYPE.ACCESS_TOKEN, this)
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			break;
			
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
