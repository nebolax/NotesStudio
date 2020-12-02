package nebolax.betternotes.notifications

import android.annotation.SuppressLint
import android.content.*
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import nebolax.betternotes.MainActivity
import java.lang.Thread.sleep

class StartupReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, NotifyService::class.java)
        context?.startService(i)
    }
}
/*
sleep(10000)
Log.i("AlexStartup", "Started yesss")
Toast.makeText(context, "created", Toast.LENGTH_LONG).show()
val intent = Intent(context, NotifyService::class.java)
val sConn = object: ServiceConnection {
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.i("AlexReceiver", "Service connected")
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.i("AlexReceiver", "Service disconnected")
    }
}
context?.startService(intent)
Log.i("AlexReceiver", context?.bindService(intent, sConn, AppCompatActivity.BIND_AUTO_CREATE).toString())

val second = Intent(context, MainActivity::class.java)
second.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
context?.startActivity(intent)*/