package nebolax.betternotes.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.SystemClock.sleep
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nebolax.betternotes.AlexLogs
import nebolax.betternotes.R
import java.lang.Exception
import java.util.*


object NotifiesModerator {
    var isStarted = false
    private var notifies = mutableListOf<AlexNotification>()
    lateinit var context: Context
    var curId = 0
    val chid = "Main notifications"

    fun setup(context: Context) {
        this.context = context
        AlexLogs.setup(context)
        if (!isStarted) {
            isStarted = true
            doSetup()
        }
    }

    fun setNotifiesMy(notifies: MutableList<AlexNotification>) {
        this.notifies = notifies
        AlexLogs.makeLog("Notifies have been loaded: $notifies")
    }

    fun setMyCurId(publ: Int) {
        curId = publ
    }

    fun addNotify(notify: AlexNotification) {
        Log.i("AlexAdd", "Added notification")
        notifies.add(notify)
        AlexStoreManager.addNotification(notify)
        AlexLogs.makeLog("Notify has been added: $notify")
    }

    fun removeAllNotify() {
        notifies.clear()
        AlexStoreManager.clearCurDir()
    }

    fun doSetup() {
        AlexStoreManager.setup(context)
        Log.i("AlexSetup", "Setup running")
        AlexLogs.makeLog("Notifies manager has been started")
        GlobalScope.launch {
            AlexLogs.makeLog("Notifies coroutine has been started")
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
        AlexLogs.makeLog("Started making notify with content: $message")
        val pendingIntent = PendingIntent.getService(
            context,
            curId,
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

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder
                .setVibrate(longArrayOf(500, 500))
                .setSound(Uri.parse("android.resource://nebolax.betternotes/raw/notify"))
        }

        AlexLogs.makeLog("Notification builder has been set up")

        try {
            NotificationManagerCompat.from(context)
                .notify(curId, builder.build())
            AlexLogs.makeLog("Notification has been successfully sent")
        } catch (e: Exception) {
            AlexLogs.makeLog("Failed to send notification")
        }

        curId++
    }
}

fun Calendar.fromDateStruct(date: DateStruct) {
    this.set(date.year, date.month, date.day, date.hours, date.minutes)
}