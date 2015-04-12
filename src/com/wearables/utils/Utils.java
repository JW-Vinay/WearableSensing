package com.wearables.utils;


public class Utils {

//	private void setAlarmManager(Context context)
//	{
//		
//		Intent myIntent = new Intent(context, AlarmService.class);
//
//		PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
//
//		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(System.currentTimeMillis());
//		calendar.add(Calendar.SECOND, 10);
//
//		alarmManager.cancel(pendingIntent);
//		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, MIN_TO_MILLISECS, MIN_TO_MILLISECS, pendingIntent);
//		// alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//		// pendingIntent);
//	}
}
