package pl.kinga.myosotis

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import pl.kinga.myosotis.NotificationReceiver
import java.util.*


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Build notification based on Intent
        val notification: Notification = Notification.Builder(context)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle(intent.getStringExtra("title"))
            .setContentText(intent.getStringExtra("text"))
            .build()
        // Show notification
        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(42, notification)
    }
    //moge sie odwolac jak do funkcji statycznej
    companion object {
        fun scheduleNotification(
            context: Context,
            hour: Int = 8,
            minutes: Int = 30,
            title: String = "Dodaj temp",
            text: String = "Dodaj swoja temp!"
        ) {
            /*val intent = Intent(context, NotificationReceiver::class.java)
            intent.putExtra("title", title)
            intent.putExtra("text", text)

            val alarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

            val requestId = System.currentTimeMillis().toInt()
            val pendingIntent =
                PendingIntent.getService(context, requestId, intent,
                    PendingIntent.FLAG_NO_CREATE)
            if (pendingIntent != null && alarmManager != null) {
                alarmManager.cancel(pendingIntent)
            }

          var alarmMgr: AlarmManager? = null
            lateinit var alarmIntent: PendingIntent

            alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(context, NotificationReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, 0)
            }

// Set the alarm to start at 8:30 a.m.
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minutes)
            }

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
            alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                1000 * 20,
                alarmIntent
            )
        }*/
            val intent = Intent(context, NotificationReceiver::class.java)
            intent.putExtra("title", title)
            intent.putExtra("text", text)
            val pending =
                PendingIntent.getBroadcast(context, 42, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            // Schdedule notification

            // Set the alarm to start at 8:30 a.m.
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minutes)
            }
            val manager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            manager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                //System.currentTimeMillis()-10,
                calendar.timeInMillis,
                //24 * 3600 * 1000,
                30*1000,
                pending
            )
        }
    }
}