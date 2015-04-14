package com.wearables.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.HashMap;
import java.util.Timer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Xml.Encoding;

import com.wearables.RefreshTokenTimer;
//import com.wearables.Constants;
import com.wearables.networking.NetworkConstants.METHOD_TYPE;
import com.wearables.networking.NetworkConstants.REQUEST_TYPE;
import com.wearables.utils.JSONParser;
import com.wearables.utils.LogUtils;
import com.wearables.utils.SharedPrefs;

public class NetworkingTask  extends AsyncTask<Object, Void, Void>
{
	private final String TAG = getClass().getSimpleName();
	
	private String mUrl;
	private boolean mShowloader;
	private METHOD_TYPE mHttpMethod;
	private REQUEST_TYPE mRequestType;
	private ProgressDialog mDialog;
	private Context mContext;
	
	public NetworkingTask(String url,boolean showloader, METHOD_TYPE httpMethod, REQUEST_TYPE requestType,Context mContext)
	{
		this.mUrl = url;
		this.mShowloader= showloader;
		this.mHttpMethod = httpMethod;
		this.mContext = mContext;
		this.mRequestType = requestType;
		
		  if (mShowloader) {
              this.mDialog = new ProgressDialog(mContext);
              this.mDialog.setCancelable(false);
          }
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		try
		{
			if(mShowloader)
			{
				mDialog.show();
			}
		}
		catch(Exception e)
		{
			
		}
			
	}
	
	@Override
	protected Void doInBackground(Object... params) {
		
		JSONObject reqObject = null;
		if(params != null && params.length > 0)
			reqObject = (JSONObject)params[0];
		String response = establishConnection(reqObject);
		System.out.println("response:\n" + response);
		JSONParser jParser;
		try {
			jParser = new JSONParser(this.mContext);
			switch(mRequestType){
				case ACCESS_TOKEN:
					jParser.parseResponse(response);
					break;
				case SP02:
					jParser.parseSP02(response);
					break;
				case BP:
					jParser.parseBP(response);
					break;
				case REFRESH_TOKEN:
					jParser.parseRefreshToken(response);
				default:
					System.out.println("default");
					break;
			}
			System.out.println("Refresh TOKEN");
			System.out.println("rtoken "+ SharedPrefs.getInstance(this.mContext).getParameters(NetworkConstants.REFRESH_TOKEN));
			System.out.println("ACCESS TOKEN");
			System.out.println(SharedPrefs.getInstance(this.mContext).getParameters(NetworkConstants.ACCESS_TOKEN));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO: manipulate responses
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
	
		try
		{
			if(mDialog.isShowing() && mShowloader)
				mDialog.dismiss();
			
			switch(mRequestType){
				case ACCESS_TOKEN:
				case REFRESH_TOKEN:
					String accessToken = SharedPrefs.getInstance(this.mContext).getParameters(NetworkConstants.ACCESS_TOKEN);
					String userID = SharedPrefs.getInstance(this.mContext).getParameters(NetworkConstants.USER_ID);
					String refreshToken = SharedPrefs.getInstance(this.mContext).getParameters(NetworkConstants.REFRESH_TOKEN);

					
//					//create a handler in an activity or fragment
//					Handler handler = new Handler();
//					
//					//create a timer task and pass the handler in
//					RefreshTokenTimer task = new RefreshTokenTimer(handler, this.mContext);
//					//use timer to run the task every 10 seconds
//					new Timer().scheduleAtFixedRate(task, 0, 10000);
					
					String SPO2_Url = NetworkUtils.generateUrl(NetworkConstants.GET_BIODATA_URL + 
							"/" + userID + "/spo2.json" , 
							NetworkUtils.getDataParams(accessToken, NetworkConstants.SPO2_SV));
			
					String BP_Url = NetworkUtils.generateUrl(NetworkConstants.GET_BIODATA_URL + 
							"/" + userID + "/bp.json" , 
							NetworkUtils.getDataParams(accessToken, NetworkConstants.BP_SV));
					new NetworkingTask(SPO2_Url, true, METHOD_TYPE.GET, REQUEST_TYPE.SP02, mContext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					new NetworkingTask(BP_Url, true, METHOD_TYPE.GET, REQUEST_TYPE.BP, mContext).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					break;
				case SP02:
					break;
					
			}
		
		}
		catch(Exception e)
		{
			
		}
		
		
		
	}
	
	 private String establishConnection(JSONObject namevaluepairs) {
         String response_str = "";
         try {
             HttpParams httpParameters = new BasicHttpParams();
             int timeoutConnection = 10000;
             HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
             int timeoutSocket = 60000;
             HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

             HttpClient client = new DefaultHttpClient(httpParameters);
             HttpResponse response = null;
             switch (mHttpMethod) {
                 case POST:
                     HttpPost mPost = new HttpPost(mUrl);
                     mPost.setEntity(new StringEntity(namevaluepairs.toString(), Encoding.UTF_8.toString()));
                     mPost.setHeader("Content-type", "application/json");
                     response = client.execute(mPost);
                     break;

                 case GET:
                     HttpGet mGet = new HttpGet(mUrl);
                     URI uri = new URI(mUrl);
                     mGet.setURI(uri);
                     response = client.execute(mGet);
                     break;

                 case DELETE:
                     HttpDelete mDelete = new HttpDelete(mUrl);
                     URI uri1 = new URI(mUrl);
                     mDelete.setURI(uri1);
                     response = client.execute(mDelete);
                     break;

                 case PUT:
                     HttpPut mPut = new HttpPut(mUrl);
                     mPut.setEntity(new StringEntity(namevaluepairs.toString(), Encoding.UTF_8.toString()));
                     mPut.setHeader("Content-type", "application/json");
                     response = client.execute(mPut);
                     break;

                 default:
                     break;
             }

             int responseCode = response.getStatusLine().getStatusCode();
             if (responseCode == HttpURLConnection.HTTP_OK
                     || responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                 InputStream is = response.getEntity().getContent();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                 StringBuilder str = new StringBuilder();
                 String line = null;
                 while ((line = reader.readLine()) != null) {
                     str.append(line + "\n");
                 }
                 is.close();

                 response_str = str.toString();
             } else if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                 response_str = "No Content";
             }
         } catch (ClientProtocolException e) {
             response_str = "Network Error" + e.getMessage();
             LogUtils.LOGE(TAG, response_str);
             // e.printStackTrace();
         } catch (SocketTimeoutException e) {
             response_str = "Network Error" + e.getMessage();
             LogUtils.LOGE(TAG, response_str);
             // e.printStackTrace();
         } catch (ConnectTimeoutException e) {
             response_str = "Network Error" + e.getMessage();
             LogUtils.LOGE(TAG, response_str);
             // e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
             response_str = "Network Error" + e.getMessage();
             LogUtils.LOGE(TAG, response_str);
             // e.printStackTrace();
         } catch (Exception e) {
             LogUtils.LOGE(TAG, response_str);
             // e.printStackTrace();
         }

         // System.out.println("Response:- " + response_str);
         return response_str;
     }
}
