package com.wearables.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.homescore.utils.PopUpListener;
import com.wearables.DataCollectService;
import com.wearables.R;
import com.wearables.networking.NetworkConstants;
import com.wearables.networking.NetworkConstants.METHOD_TYPE;
import com.wearables.networking.NetworkConstants.REQUEST_TYPE;
import com.wearables.networking.NetworkUtils;
import com.wearables.networking.NetworkingTask;
import com.wearables.utils.Constants;
import com.wearables.utils.Constants.SERVICE_ACTIONS;
import com.wearables.utils.PopUp;
import com.wearables.utils.SharedPrefs;

public class MainActivity extends Activity implements OnClickListener {

	private Button mBPMonitoringBtn, mBloodOxygenMonitoringBtn, mPiPBtn,
			mDashboardBtn, mWithingsBtn;

	private TextView mBioMetricDetailsView;
	private BroadcastReceiver mReceiver =new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			mBioMetricDetailsView.setText(intent.getExtras().getString(
					"summary"));

		}};
	
		private int mViewClicked = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setActionBar();
		mBioMetricDetailsView = (TextView) findViewById(R.id.biometricDetailsView);

		mWithingsBtn = (Button) findViewById(R.id.withingsBtn);
		mWithingsBtn.setOnClickListener(this);
		mBPMonitoringBtn = (Button) findViewById(R.id.ihealthBPBtn);
		mBloodOxygenMonitoringBtn = (Button) findViewById(R.id.ihealthBOBtn);
		mBPMonitoringBtn.setOnClickListener(this);
		mBloodOxygenMonitoringBtn.setOnClickListener(this);
		mPiPBtn = (Button) findViewById(R.id.pipBtn);
		mDashboardBtn = (Button) findViewById(R.id.dashboardBtn);
		mDashboardBtn.setOnClickListener(this);
		mPiPBtn.setOnClickListener(this);

		// Initiate Service to manage bioharness
		Intent intent = new Intent(this, DataCollectService.class);
		intent.putExtra(Constants.INTENT_TASK_ACTION,
				SERVICE_ACTIONS.START_SERVICE);
		startService(intent);
	}

	private void initiateDataPush(int id) {
		long currentTime = System.currentTimeMillis()/1000;
		
		if (TextUtils.isEmpty(SharedPrefs.getInstance(MainActivity.this)
				.getParameters(NetworkConstants.ACCESS_TOKEN))) {
			loadWebViewForAuth(id);

		} else {
			int diff = (int) (System.currentTimeMillis() - SharedPrefs
					.getInstance(MainActivity.this).getLongParameters(
							NetworkConstants.TIMESTAMP));

			/*
			 * If the time is greater than access token expiry and call refresh
			 * the access token
			 */
			if (diff > Constants.EXPIRY_TIME) {
				String url = NetworkUtils.generateUrl(
						NetworkConstants.USER_AUTH_URL,
						NetworkUtils.getRefreshTokenParams(MainActivity.this));

				if (id == R.id.ihealthBOBtn) {
					new NetworkingTask(url, true, METHOD_TYPE.GET,
							REQUEST_TYPE.REFRESH_TOKEN_BP, MainActivity.this)
							.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}else{
					new NetworkingTask(url, true, METHOD_TYPE.GET,
							REQUEST_TYPE.REFRESH_TOKEN_BO, MainActivity.this)
							.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}
			} else {
				String accessToken = SharedPrefs.getInstance(MainActivity.this)
						.getParameters(NetworkConstants.ACCESS_TOKEN);
				String userID = SharedPrefs.getInstance(MainActivity.this)
						.getParameters(NetworkConstants.USER_ID);

				String url = "";
				REQUEST_TYPE reqType = null;
				if (id == R.id.ihealthBOBtn) {
					url = NetworkUtils.generateUrl(
							NetworkConstants.GET_BIODATA_URL + "/" + userID
									+ "/spo2.json", NetworkUtils.getDataParams(
									accessToken, NetworkConstants.SPO2_SV, MainActivity.this));

					reqType = REQUEST_TYPE.SP02;

				} else {
					url = NetworkUtils.generateUrl(
							NetworkConstants.GET_BIODATA_URL + "/" + userID
									+ "/bp.json", NetworkUtils.getDataParams(
									accessToken, NetworkConstants.BP_SV, MainActivity.this));
					reqType = REQUEST_TYPE.BP;
				}

				new NetworkingTask(url, true, METHOD_TYPE.GET, reqType,
						MainActivity.this)
						.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
		}

	}

	private void loadWebViewForAuth(int id) {
		Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
		String url = NetworkUtils.generateUrl(NetworkConstants.USER_AUTH_URL,
				NetworkUtils.getAuthorizationParams()); // TODO:
														// Change
														// here
		intent.putExtra("url", url);
		intent.putExtra("id", id);
		startActivityForResult(intent, 100);
	}

	@Override
	public void onClick(final View v) {
		long timestamp = System.currentTimeMillis()/1000 - 4*60*60;
		SharedPrefs sp = SharedPrefs.getInstance(this);
		sp.setParameters("currentTime", String.valueOf(timestamp));
		Intent intent = null;
		switch (v.getId()) {
		case R.id.withingsBtn:
			//Edit
			try
			{
				intent = getPackageManager().getLaunchIntentForPackage("iHealthMyVitals.V2");
//				if(intent != null)
				{
					NetworkUtils.getAndPostWithingsData(MainActivity.this);
				}
//				else
//				{
//					//TODO:
//				}
			}
			catch(Exception e)
			{
				
			}
			
			break;
		case R.id.ihealthBPBtn:
			mViewClicked = v.getId();
			try
			{
				intent = getPackageManager().getLaunchIntentForPackage("iHealthMyVitals.V2");
				 startActivity(intent);
			}
			catch(ActivityNotFoundException e)
			{
				PopUp popup = new PopUp(MainActivity.this, new PopUpListener() {
					
					@Override
					public void onPoisitiveBtnClicked() {
						//install application
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("market://details?id=" + "iHealthMyVitals.V2"));
						startActivity(intent);
						mViewClicked = -1;
					}
					
					@Override
					public void onNeutralBtnClicked() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onNegativeBtnClicked() {
						// TODO Auto-generated method stub
						
					}
				}, getString(R.string.tag_download_iHealth_msg), getString(R.string.tag_ok));
				popup.show();
				
			}
			break;
		case R.id.ihealthBOBtn:
//			Toast.makeText(MainActivity.this, getString(R.string.tag_measure_po), Toast.LENGTH_SHORT).show();
			PopUp popup = new PopUp(MainActivity.this, new PopUpListener() {
				
				@Override
				public void onPoisitiveBtnClicked() {
					initiateDataPush(v.getId());
				}
				
				@Override
				public void onNeutralBtnClicked() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onNegativeBtnClicked() {
					// TODO Auto-generated method stub
					
				}
			}, R.string.tag_measure_po, R.string.tag_yes, R.string.tag_return);
			popup.show();
//			initiateDataPush(v.getId());
			break;
		case R.id.dashboardBtn:
			break;
		case R.id.pipBtn:
			 intent = new Intent(this, PIPMeasurementsActivity.class);
			 startActivity(intent);
			break;
		}
	}

	private void setActionBar() {
		ActionBar actionbar = getActionBar();
		actionbar.setTitle(getString(R.string.tag_cura));
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_drawable));
		actionbar.setHomeButtonEnabled(false);
		actionbar.setIcon(R.drawable.ic_launcher);
		actionbar.show();
	}

	@Override
	protected void onStart() {

		super.onStart();
		IntentFilter filter = new IntentFilter("com.wearable.ui");
		registerReceiver(mReceiver, filter);
		
		if(mViewClicked != -1)
		{
			initiateDataPush(mViewClicked);
			mViewClicked = -1;
		}
		// IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		// registerReceiver(mReceiver, filter);
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(mReceiver);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		
//		case 200:
//			System.out.println("resultCode: " + resultCode);
//			break;
		case 100:
			if(resultCode == RESULT_OK)
			{
				String code = data.getStringExtra("code");
				int id = data.getIntExtra("id", -1);
				
				if(R.id.ihealthBPBtn == id){
					String url = NetworkUtils.generateUrl(
							NetworkConstants.USER_AUTH_URL,
							NetworkUtils.getAccessTokenParams(code));
					new NetworkingTask(url, true, METHOD_TYPE.GET,
							REQUEST_TYPE.ACCESS_TOKEN_BP, this)
							.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}else{
					String url = NetworkUtils.generateUrl(
							NetworkConstants.USER_AUTH_URL,
							NetworkUtils.getAccessTokenParams(code));
					new NetworkingTask(url, true, METHOD_TYPE.GET,
							REQUEST_TYPE.ACCESS_TOKEN_SPO2, this)
							.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}

			}
			
			break;

		case 300:
			// discoverable now
			break;
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

}
