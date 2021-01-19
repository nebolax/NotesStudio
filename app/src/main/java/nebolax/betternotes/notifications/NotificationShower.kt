package nebolax.betternotes.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import nebolax.betternotes.R
import nebolax.betternotes.notifications.database.DatabaseNotification
import nebolax.betternotes.notifications.database.NotifiesDatabase

class NotificationShower: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("aaalarm", "receiver invoked")

        val notifyContent = DatabaseNotification.fromJsoned(intent!!.getStringExtra("notify").toString()).toAlexNotification()

        val notifyIntent = PendingIntent.getBroadcast(
            context,
            notifyContent.id,
            Intent(),
            PendingIntent.FLAG_UPDATE_CURRENT)

        var title = notifyContent.message
        if (title.isEmpty()) title = "Note without title"

        val builder = NotificationCompat.Builder(context!!, "main_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(notifyIntent)
            .setChannelId("main_channel")
            .setContentTitle("Incoming notification!")
            .setContentText(title)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder
                .setSound(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.notify))
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
        }

        NotificationManagerCompat.from(context).notify(notifyContent.id, builder.build())
        NotifiesManager.getInstance(context, NotifiesDatabase.getInstance(context)).deleteNotification(notifyContent.id)
    }
}