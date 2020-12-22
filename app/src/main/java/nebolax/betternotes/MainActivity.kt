package nebolax.betternotes

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("statuss", "creating")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        val alarmIntent = Intent(this, NotificationPublisher::class.java)

        val scTime: Long = SystemClock.elapsedRealtime() + 5000
        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0)
        (this.getSystemService(ALARM_SERVICE) as AlarmManager).setExactAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            scTime,
            pendingIntent
        )
    }
}


class NotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(Intent(context, CustomService::class.java))
        } else {
            context.startService(Intent(context, CustomService::class.java))
        }
        Log.i("sttas", "received")
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = getNotification("test azaza beset", context)
        val id = 0
        notificationManager.notify(id, notification)
    }

    private fun getNotification(content: String, context: Context): Notification? {
        val builder = NotificationCompat.Builder(context)
        builder.setContentTitle("Scheduled Notification")
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        return builder.build()
    }

    companion object {
        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
    }
}

class CustomService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        Log.i("sservice", "binded")
        return Binder()
    }

    override fun sendBroadcast(intent: Intent?) {
        super.sendBroadcast(intent)
        Log.i("sservice", "dfjnfe")
    }



    override fun onCreate() {
        Log.i("sservice", "created")
    }
}