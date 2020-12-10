package nebolax.betternotes.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import nebolax.betternotes.AlexLogs


class NotifyService: Service() {
    override fun onBind(intent: Intent?): IBinder {
        Log.i("AlexService", "Binded")
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        setupForeground()
        AlexLogs.setup(this)
        AlexLogs.makeLog("Program service has been created")
        Log.i("AlexService", "Created")
        NotifiesModerator.setup(this)
    }

    private fun setupForeground() {
        if (Build.VERSION.SDK_INT >= 26) {
            val CHANNEL_ID = "my_channel_01"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
            val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("")
                .setContentText("").build()
            startForeground(1, notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        AlexLogs.makeLog("Program service has been destroyed")
        Log.i("AlexService", "Destroyed")

    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.i("AlexService", "Rebinded")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("AlexService", "Unbinded")
        return super.onUnbind(intent)
    }
}