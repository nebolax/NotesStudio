package nebolax.betternotes.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.SystemClock.sleep
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import events.ServCheck
import events.emit
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nebolax.betternotes.R
import java.util.*


object NotifiesModerator {
    var isStarted = false
    private var notifies = mutableListOf<AlexNotification>()
    lateinit var context: Context
    var notifiesPublished = 0
    val chid = "Main notifications"

    fun setup(context: Context) {
        this.context = context
        if (!isStarted) {
            isStarted = true
            doSetup()
        }
    }

    fun setNotifiesMy(notifies: MutableList<AlexNotification>) {
        this.notifies = notifies
    }

    fun addNotify(notify: AlexNotification) {
        Log.i("AlexAdd", "Added notification")
        notifies.add(notify)
        AlexStoreManager.addNotification(notify)
    }

    fun removeAllNotify() {
        notifies.clear()
        AlexStoreManager.clearCurDir()
    }

    fun doSetup() {
        AlexStoreManager.setup(context)
        Log.i("AlexSetup", "Setup running")
        GlobalScope.launch {
            while (true) {
                val calCur = Calendar.getInstance()
                var curNotify: AlexNotification? = null
                notifies.forEach {
                    val calComp = Calendar.getInstance()
                    calComp.fromDateStruct(it.date)
                    if (calComp <= calCur) {
                        callNotify(it.message)
                        curNotify = it
                        return@forEach
                    }
                }
                if (curNotify != null) {
                    notifies.remove(curNotify)
                    AlexStoreManager.removeNotify(curNotify!!)
                }
                sleep(1000)
            }
        }
    }

    fun callNotify(message: String) {
        Log.i("AlexCall", "Calling notify")
        val pendingIntent = PendingIntent.getService(
            context,
            notifiesPublished,
            Intent(),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(
            context,
            chid
        )   
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("It's time to:")
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(500, 500))
            .setSound(Uri.parse("android.resource://nebolax.betternotes/raw/notify"))

        NotificationManagerCompat.from(context)
            .notify(notifiesPublished, builder.build())

        notifiesPublished++
    }
}

fun Calendar.fromDateStruct(date: DateStruct) {
    this.set(date.year, date.month, date.day, date.hours, date.minutes)
}