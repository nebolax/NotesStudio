package nebolax.betternotes.alarmsNew

import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import nebolax.betternotes.notifications.NotifiesModerator


class MyNewIntentService : Service() {
    fun callNotify(message: String) {
        Log.i("AlexCall", "Calling notify")
        val pendingIntent = PendingIntent.getService(
            this,
            0,
            Intent(),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(
            this,
            "channel"
        )
            .setSmallIcon(nebolax.betternotes.R.drawable.ic_launcher_foreground)
            .setContentTitle("It's time to:")
            .setContentText(message)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder
                .setVibrate(longArrayOf(500, 500))
                .setSound(Uri.parse("android.resource://nebolax.betternotes/raw/notify"))
        }

        NotificationManagerCompat.from(this)
            .notify(0, builder.build())
    }

    override fun onBind(intent: Intent?): IBinder? {
        return Binder()
    }

    override fun onCreate() {
        val prefs = application.getSharedPreferences("nebolax.betternotes", MODE_PRIVATE)
        val curTime = System.currentTimeMillis()
        val delta = curTime - prefs.getLong("start_time", curTime + 98)
        Log.i("AlexCall", delta.toString())
        callNotify("test message")
    }
}


class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val intent1 = Intent(context, MyNewIntentService::class.java)
        context.startService(intent1)
    }
}