package nebolax.betternotes.notifications

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import nebolax.betternotes.notifications.database.DatabaseNotification

class NotificationShower: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("aaalarm", "receiver invoked")

        val notifyContent = DatabaseNotification.fromJsoned(intent!!.getStringExtra("notify").toString()).toAlexNotification()

        val notifyIntent = PendingIntent.getBroadcast(
            context,
            notifyContent.id,
            Intent(),
            PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context!!, "main_channel")
            .setSmallIcon(nebolax.betternotes.R.drawable.ic_launcher_foreground)
            .setContentIntent(notifyIntent)
            .setContentTitle("Incoming notification!")
            .setContentText(notifyContent.message)

        NotificationManagerCompat.from(context).notify(notifyContent.id, builder.build())
    }
}