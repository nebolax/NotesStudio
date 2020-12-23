package nebolax.betternotes.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import nebolax.betternotes.notifications.database.NotifiesDatabase


class StartupReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val alexManager = NotifiesManager.getInstance(context!!, NotifiesDatabase.getInstance(context))
        alexManager.loadAllNotifies()
    }
}