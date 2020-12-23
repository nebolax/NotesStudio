package nebolax.betternotes.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import nebolax.betternotes.notifications.database.NotifiesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nebolax.betternotes.notifications.database.DatabaseNotification
import java.lang.Exception
import java.util.*

class NotifiesManager private constructor(
    private val context: Context,
    private val database: NotifiesDatabase
)
{
    private val notifiesManagerScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private val systemAlarm: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun setAlarm(notify: DatabaseNotification) {
        val intent = Intent(context, NotificationShower::class.java)
            .putExtra("notify", notify.jsoned)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notify.notifyId,
            intent,
            0
        )

        systemAlarm.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
            (notify.callTimeMillis - System.currentTimeMillis()) + SystemClock.elapsedRealtime(),
            pendingIntent)
        Log.i("AlarmSetter", "set alarm to ${notify.toAlexNotification().timeToCall.get(Calendar.MINUTE)}")
    }

    fun loadAllNotifies() {
        notifiesManagerScope.launch {
            val stored = database.dao.getAllNotifies()
            stored.forEach { setAlarm(it) }
        }
    }

    private fun cancelAlarm(notifyId: Int) {
        Log.i("aaalarms", "canceling alarm: $notifyId")
        val intent = Intent(context, NotificationShower::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notifyId,
            intent,
            0
        )
        systemAlarm.cancel(pendingIntent)
    }

    fun addNotification(notify: AlexNotification) {
        notifiesManagerScope.launch {
            database.dao.insert(notify.toDatabaseNotify())
            setAlarm(notify.toDatabaseNotify())
        }
    }

    fun deleteNotification(notifyId: Int) {
        notifiesManagerScope.launch {
            cancelAlarm(notifyId)
            database.dao.deleteNotify(notifyId)
        }
    }

    fun deleteAllPending() {
        notifiesManagerScope.launch {
            val stored = database.dao.getAllNotifies()
            stored.forEach { cancelAlarm(it.notifyId) }
            database.dao.clearAll()
        }
    }

    companion object {
        private var instance: NotifiesManager? = null
        fun getInstance(context: Context, database: NotifiesDatabase): NotifiesManager {
            if (instance == null) {
                instance = NotifiesManager(context, database)
            }
            return instance!!
        }
    }
}