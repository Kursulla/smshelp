package com.eutechpro.smshelp.sms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import java.util.*

class SmsControll {
    companion object{
        private const val NUMBER_TO_SEND_TO = "+4917637206586"
    }

    fun sendSms() {
        val smsManager = SmsManager.getDefault()

        smsManager.sendTextMessage(NUMBER_TO_SEND_TO, null, "sms message", null, null)
    }

    fun scheduleNextAlarm(context: Context){
        /*
        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();
        cal.set(2012,2,8,18,16);

        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmServiceReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
       // cal.add(Calendar.SECOND, 5);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

         */
        Log.d("SmsControll","Scheduling alarm")
//        val calendar: Calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        calendar.clear()
//        calendar.set(2017,5,23,23,57,40)
//        Log.d("SmsControll",""+calendar.get(Calendar.MINUTE))
        val intent = Intent(context, AlarmServiceReceiver::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,intent,0)

        val alarmManager:AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

//        alarmManager.setInexactRepeating(
//                AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_HALF_HOUR, pendingIntent)


        // Set the alarm to start at 8:30 a.m.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
//        calendar.set(Calendar.HOUR_OF_DAY, 0)
//        calendar.set(Calendar.MINUTE, 3)
        Log.d("SmsControll",calendar.toString())
// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis+3000, 1000 * 60, pendingIntent)

    }
}