package com.wearables;

import java.util.TimerTask;

import com.wearables.networking.NetworkConstants;
import com.wearables.networking.NetworkUtils;
import com.wearables.networking.NetworkingTask;
import com.wearables.networking.NetworkConstants.METHOD_TYPE;
import com.wearables.networking.NetworkConstants.REQUEST_TYPE;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

public class RefreshTokenTimer extends TimerTask {
	private Handler handler;
	private Context mContext;

	public RefreshTokenTimer(Handler handler, Context context) {
		this.handler = handler;
		this.mContext = context;
	}

	@Override
	public void run() {
		//update UI using the handler
	      handler.post(new Runnable() {
//	         private Context mContext;

			public void run() {					
	            //update UI here.
	        	System.out.println("In run()");
	     		String url = NetworkUtils.generateUrl(NetworkConstants.USER_AUTH_URL,
	     				NetworkUtils.getRefreshTokenParams(mContext));
	     		System.out.println("URL for refresh "+url);
	     		new NetworkingTask(url, true, METHOD_TYPE.GET,
	     				REQUEST_TYPE.REFRESH_TOKEN, mContext)
	     				.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	         }
	      });
	}

}
