package nebolax.betternotes.notifications

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class SimpleService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return Binder()
    }

    override fun onCreate() {

    }
}