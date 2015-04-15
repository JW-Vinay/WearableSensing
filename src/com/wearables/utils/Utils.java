package com.wearables.utils;

import java.text.SimpleDateFormat;
import java.util.Date;



public class Utils {

//	public static void setAlarmManager(Context context)
//	{
//		
//		Intent myIntent = new Intent(context, AlarmService.class);
//
//		PendingIntent pendingIntent = PendingIntent.getService(context, 0, myIntent, 0);
//
//		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(System.currentTimeMillis());
//		calendar.add(Calendar.SECOND, 10);
//
//		alarmManager.cancel(pendingIntent);
//		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, Constants.INITIAL_START_TIME, Constants.INTERVAL_MILLIS, pendingIntent);
//	}
	
	public static String getFormattedTime(long time)
	{
		try
		{
			Date d = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			return sdf.format(d);
		}
		catch(Exception e)
		{
			
		}
		
		return "";
	}
}
