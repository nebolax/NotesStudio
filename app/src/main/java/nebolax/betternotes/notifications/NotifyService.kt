package nebolax.betternotes.notifications

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class NotifyService: Service() {
    override fun onBind(intent: Intent?): IBinder {
        Log.i("AlexService", "Binded")
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("AlexService", "Created")
        NotifiesModerator.setup(this)
    }

    override fun onDestroy() {
        super.onDestroy()
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