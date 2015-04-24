package com.wearables;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.parse.ParsePushBroadcastReceiver;
import com.wearables.utils.LogUtils;

public class ParseCustomBroadcastReceiver extends ParsePushBroadcastReceiver
{
	@Override
	protected void onPushReceive(Context context, Intent intent) 
	{
		super.onPushReceive(context, intent);
		Bundle bundle = intent.getExtras();
		String data = bundle.getString("com.parse.Data");
		System.out.println("Data: " + data);
		buildNotification(context, data);
	}
	
	
	
	private void buildNotification(Context context, String jsonResponse)
	{
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker(context.getString(R.string.ticker_text));
		builder.setLights(context.getResources().getColor(R.color.color_red), 100, 100);
		builder.setContentTitle(parseNotification(jsonResponse));
		builder.setShowWhen(true);
		
		builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/"+ R.raw.alert));
		builder.setContentText("Needs Help!!");
		builder.setCategory(NotificationCompat.CATEGORY_ALARM);

		
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:4126084234"));
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		builder.setAutoCancel(true);
		
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(100, builder.build());
		
	}
	
	public String parseNotification(String data)
	{
		try
		{
			JSONObject object = new JSONObject(data);
			if(object != null)
			{
				String username = object.getString("user_name");
				String text = object.getString("text");
				return username + " - " + text;
			}
		}
		catch(JSONException e)
		{
			LogUtils.LOGE("Notification TAG", "" + e.getMessage());
		}
		
		return "";
	}
}
