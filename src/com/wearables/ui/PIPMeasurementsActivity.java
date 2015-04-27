package com.wearables.ui;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.galvanic.pipsdk.PIP.Pip;
import com.galvanic.pipsdk.PIP.PipAnalyzerListener;
import com.galvanic.pipsdk.PIP.PipAnalyzerOutput;
import com.galvanic.pipsdk.PIP.PipConnectionListener;
import com.galvanic.pipsdk.PIP.PipInfo;
// PIP-specific imports
import com.galvanic.pipsdk.PIP.PipManager;
import com.galvanic.pipsdk.PIP.PipManagerListener;
import com.galvanic.pipsdk.PIP.PipStandardAnalyzer;
import com.wearables.R;
import com.wearables.networking.NetworkUtils;
import com.wearables.utils.Utils;

public class PIPMeasurementsActivity extends Activity implements
		PipManagerListener, PipConnectionListener, PipAnalyzerListener {

	JSONObject stressdata;
	public int stressedcount = 0;
	public int relaxedcount = 0;
	public int steadycount = 0;

	private static final long TIME_PERIOD = 30 * 1000;
	// Singleton instance of PipManager object.
	private PipManager pipManager = null;
	// We will only be discovering a single PIP in this app.
	private boolean pipDiscovered = false;

	// Minimal UI implementation.
	Button buttonDiscover;
	Button buttonDisconnect;
	TextView textViewStatus;

	Handler handler = new Handler();

	private Runnable mDisconnectRunnable = new Runnable() {

		@Override
		public void run() {
			handler.removeCallbacks(null);
			pipManager.resetManager();
			finish();

		}
	};
	private Runnable mPipRunnable = new Runnable() {
		@Override
		public void run() {
			pipManager.getPip(pipManager.getDiscoveryAtIndex(0).pipID)
					.disconnect();
			buttonDiscover.setEnabled(true);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pipsdkexample);
		setActionBar();
		textViewStatus = (TextView) findViewById(R.id.messageTextView);
		buttonDiscover = (Button) findViewById(R.id.stressMeasureBtn);
		buttonDiscover.setEnabled(true);
		pipManager = PipManager.getInstance();
		pipManager.initialize(this, this);

		// TODO: Uncomment for testing data
		// handler.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// try
		// {
		// stressdata = new JSONObject();
		// stressdata.put("user_name", "mshrimal");
		// stressdata.put("stress_score", 0);
		// stressdata.put("skin_conductance", 0);
		// stressdata.put("duration", 120);
		// stressdata.put("number_relax_events", 10);
		// stressdata.put("number_stress_events", 20);
		// stressdata.put("number_steady_events", steadycount);
		// stressdata.put("time_recorded", "2015-04-12 20:52:41");
		// stressdata.put("time_received", "2015-04-12 20:52:48");
		//
		// NetworkUtils.postStressMeasurementData(
		// PIPMeasurementsActivity.this, stressdata);
		//
		// }
		// catch(JSONException e)
		// {
		// e.printStackTrace();
		// }
		//
		// }
		// }, 1000);

		// Kick off a PIP discovery process when the Discover button is clicked.
		buttonDiscover.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				pipDiscovered = false;
				buttonDiscover.setEnabled(false);
				pipManager.resetManager();
				textViewStatus.setText("Discovering...");
				pipManager.discoverPips();
				if (pipDiscovered) {

					handler.postDelayed(mPipRunnable, TIME_PERIOD);

				}
			}
		});

		handler.postDelayed(mDisconnectRunnable, TIME_PERIOD);
	}

	// Sets the action bar according to the standard UI of CURA app.
	@SuppressWarnings("deprecation")
	private void setActionBar() {
		ActionBar actionbar = getActionBar();
		actionbar.setTitle(getString(R.string.tag_cura));
		actionbar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_drawable));
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setIcon(R.drawable.ic_launcher);
		actionbar.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void connectPip() {
		// We stop discovery after the first PIP has been found, so
		// the list of discovered PIPs will contain a single entry at index
		// zero.
		Pip pip = pipManager.getPip(pipManager.getDiscoveryAtIndex(0).pipID);
		// Register listeners for connection and data analysis events.
		pip.setPipAnalyzerListener(this);
		pip.setPipConnectionListener(this);
		// Connect to the PIP.
		pip.connect();
	}

	// *************************************************
	// * PipManagerListener interface implementation
	// *************************************************

	// Once this event is raised, the PipManager object is initialized
	// and ready for use by the application.
	@Override
	public void onPipManagerReady() {
		textViewStatus.setText("Ready.");
	}

	// This event is raised when pairing with a PIP has successfully
	// completed.
	@Override
	public void onPipPaired(int status, int pipID) {
	}

	// This event is raised when a PIP has been discovered. For simplicity
	// in the current app, we terminate discovery after a single PIP has been
	// found, but in general, the discovery process continues until all
	// PIPs in range have been found.
	@Override
	public void onPipDiscovered() {
		// We have found our first PIP - terminate discovery.
		pipManager.cancelDiscovery();

		String statusMsg = "Discovered PIP: ";
		PipInfo info = pipManager.getDiscoveryAtIndex(0);
		if (info != null) {
			if (info.name != null && info.name.length() != 0)
				statusMsg = statusMsg.concat(info.name);
			else
				statusMsg.concat("Unknown PIP");
		}

		textViewStatus.setText(statusMsg);
		pipDiscovered = true;
		buttonDiscover.setEnabled(true);
		buttonDiscover.setEnabled(false);
		textViewStatus.setText("Connecting...");
		connectPip();
	}

	// onPipDiscoveryComplete is fired when a discovery process ends.
	// In this case, check whether or not at least one PIP was found -
	// if not, then display an appropriate message.
	@Override
	public void onPipDiscoveryComplete(int numDiscovered) {
		if (!pipDiscovered) {
			textViewStatus
					.setText("Can't find PIP\nSwitch on Pip\nOR\nSwitch on Blutooth");
			buttonDiscover.setEnabled(true);
		}

		// buttonDiscover.setEnabled(true);
	}

	// onPipsResumed will be called when the application resumes
	// from the suspended state. The SDK automatically attempts to
	// re-connect to any PIPs that were connected prior to the app
	// suspending.
	@Override
	public void onPipsResumed(int status) {
	}

	// *************************************************
	// * PipConnectionListener interface implementation
	// *************************************************

	// This event is raised when a connection attempt to a PIP
	// completes.
	@Override
	public void onPipConnected(int status, int pipID) {
		Pip pip = pipManager.getPip(pipID);
		if (pip != null)
			pip.startStreaming();
		textViewStatus.setText("Connected.");
	}

	// This event is raised when a connection attempt to a PIP fails.
	// This allows us to configure the UI appropriately.
	@Override
	public void onPipConnectionError(int status, int pipID) {
		buttonDiscover.setEnabled(true);
		textViewStatus.setText("Connect failed.");
	}

	// This event is raised when a connection to a PIP is terminated.
	@Override
	public void onPipDisconnected(int status, int pipId) {
		textViewStatus.setText("Disconnected.");
		pipManager.resetManager();
		finish();
		handler.removeCallbacks(mPipRunnable);
	}

	// *************************************************
	// * PipAnalyzerListener interface implementation
	// *************************************************

	// This event is raised when the PIP's signal analyzer processes
	// new sample data and updates its output(s). While it is not
	// a requirement that an analyzer generate an event on a per-sample
	// basis, the SDK's standard analyzer does so.
	@Override
	public void onAnalyzerOutputEvent(int pipID, int status) {
		stressdata = new JSONObject();
		String currentTime = Utils.getFormattedTime(System.currentTimeMillis());
		if (pipManager.getPip(pipID).isActive()) {
			// Retrieve the analyzer's current output
			ArrayList<PipAnalyzerOutput> op = pipManager.getPip(pipID)
					.getAnalyzerOutput();
			// Get the analyzer's CURRENT_TREND output
			int currentTrendEvent = (int) op
					.get(PipStandardAnalyzer.CURRENT_TREND_EVENT.ordinal()).outputValue;

			// Update the UI based on the current trend - relaxing, stressing
			// or constant.
			if (PipAnalyzerListener.STRESS_TREND_RELAXING == currentTrendEvent) {
				textViewStatus.setText("Streaming: Relaxing");
				relaxedcount++;
				float stresscore = (stressedcount * 100)
						/ (relaxedcount + stressedcount);
				try {

					stressdata.put("user_name", "mshrimal");
					stressdata.put("stress_score", String.valueOf(stresscore));
					stressdata.put("skin_conductance", String.valueOf(0));
					stressdata.put("duration", String.valueOf(30));
					stressdata.put("number_relax_events",
							String.valueOf(relaxedcount));
					stressdata.put("number_stress_events",
							String.valueOf(stressedcount));
					stressdata.put("number_steady_events",
							String.valueOf(steadycount));
					stressdata.put("time_recorded", currentTime);
					stressdata.put("time_received", currentTime);

					NetworkUtils.postStressMeasurementData(
							PIPMeasurementsActivity.this, stressdata);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else if (PipAnalyzerListener.STRESS_TREND_STRESSING == currentTrendEvent) {
				textViewStatus.setText("Streaming: Stressing");
				stressedcount++;
				float stresscore = (stressedcount * 100)
						/ (relaxedcount + stressedcount);
				try {
					stressdata.put("user_name", "mshrimal");
					stressdata.put("stress_score", String.valueOf(stresscore));
					stressdata.put("skin_conductance", String.valueOf(0));
					stressdata.put("duration", String.valueOf(30));
					stressdata.put("number_relax_events",
							String.valueOf(relaxedcount));
					stressdata.put("number_stress_events",
							String.valueOf(stressedcount));
					stressdata.put("number_steady_events",
							String.valueOf(steadycount));
					stressdata.put("time_recorded", currentTime);
					stressdata.put("time_received", currentTime);

					NetworkUtils.postStressMeasurementData(
							PIPMeasurementsActivity.this, stressdata);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				// User is holding PIP and samples are being received,
				// but the user is neither stressing nor relaxing.
				textViewStatus.setText("Streaming: Active");
				steadycount++;
			}

		} else {

			textViewStatus.setText("Streaming: Inactive - "
					+ getString(R.string.pip_inactive_msg));
		}
	}

	public JSONObject getStressdata() {
		return stressdata;
	}

}