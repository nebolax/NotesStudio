package nebolax.betternotes.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class SimpleService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return Binder()
    }

    override fun onCreate() {

    }
}