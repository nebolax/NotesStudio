package nebolax.betternotes.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import nebolax.betternotes.notifications.database.NotifiesDatabase


class StartupReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        NotifiesManager.greet(context!!)
        Toast.makeText(context, "Succ loaded", Toast.LENGTH_LONG).show()
        val alexManager = NotifiesManager.getInstance(context, NotifiesDatabase.getInstance(context))
        alexManager.setupAllNotifies()
    }
}