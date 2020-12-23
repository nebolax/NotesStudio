package nebolax.betternotes
//
//import android.annotation.SuppressLint
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import android.media.AudioAttributes
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//
//
//class MainActivity : AppCompatActivity() {
//    private var prefs: SharedPreferences? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        prefs = getSharedPreferences("nebolax.betternotes", MODE_PRIVATE)
//    }
//
//    @SuppressLint("QueryPermissionsNeeded")
//    override fun onResume() {
//        super.onResume()
//        if (prefs!!.getBoolean("firstrun", true)) {
//            setupChannel()
//            prefs!!.edit().putBoolean("firstrun", false).apply()
//        } else {
//            Log.i("AlexFOpen", "It's not first run")
//        }
//    }
//
//    private fun setupChannel() {
//        // This is required only for android Oreo anf higher
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val descriptionText = "no description azazaza"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            //Create channel
//            val channel = NotificationChannel(
//                "main_channel",
//                "Huh dude you are dude",
//                importance)
//            .apply {
//                description = descriptionText
//                enableVibration(true)
//                vibrationPattern = longArrayOf(500, 500)
//                setSound(
//                    Uri.parse("android.resource://nebolax.betternotes/raw/notify"),
//                    AudioAttributes.Builder()
//                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
//                        .build())
//            }
//            //Reg channel
//            val notificationManager: NotificationManager =
//                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//}

import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat


class MainActivity : Activity() {
    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("statuss", "creating")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = getSharedPreferences("nebolax.betternotes", MODE_PRIVATE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        val alarmIntent = Intent(this, NotificationPublisher::class.java)
        val scTime: Long = SystemClock.elapsedRealtime() + 5000
        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0)
        (this.getSystemService(ALARM_SERVICE) as AlarmManager).setExactAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            scTime,
            pendingIntent
        )
    }
    @SuppressLint("QueryPermissionsNeeded")
    override fun onResume() {
        super.onResume()
        if (prefs.getBoolean("firstrun", true)) {
            Log.i("AlexFOpen", "Trying to open security app")
            val builder = AlertDialog.Builder(this)
                .setMessage("Please, allow this app to run in background to receive notifications!")
                .setPositiveButton("Yes") { _, _ ->
                    val manufacturer: String = Build.MANUFACTURER
                    try {
                        var intent = Intent()
                        if ("xiaomi".equals(manufacturer, ignoreCase = true)) {
                            intent.component = ComponentName(
                                "com.miui.securitycenter",
                                "com.miui.permcenter.autostart.AutoStartManagementActivity"
                            )
                        } else if ("oppo".equals(manufacturer, ignoreCase = true)) {
                            intent.component = ComponentName(
                                "com.coloros.safecenter",
                                "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                            )
                        } else if ("vivo".equals(manufacturer, ignoreCase = true)) {
                            intent.component = ComponentName(
                                "com.vivo.permissionmanager",
                                "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                            )
                        } else if ("Letv".equals(manufacturer, ignoreCase = true)) {
                            intent.component = ComponentName(
                                "com.letv.android.letvsafe",
                                "com.letv.android.letvsafe.AutobootManageActivity"
                            )
                        } else if ("Honor".equals(manufacturer, ignoreCase = true)) {
                            intent.component = ComponentName(
                                "com.huawei.systemmanager",
                                "com.huawei.systemmanager.optimize.process.ProtectActivity"
                            )
                        } else if ("meizu".equals(manufacturer, ignoreCase = true)) {
                            intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
                            intent.addCategory(Intent.CATEGORY_DEFAULT)
                            intent.putExtra("packageName", BuildConfig.APPLICATION_ID)
                        }
                        val list = packageManager.queryIntentActivities(
                            intent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        )
                        if (list.size > 0) {
                            startActivity(intent)
                        }
                    } catch (e: Exception) {
                        Log.i("AlexException", e.message.toString())
                    }
                }
                .setNegativeButton("No") { _, _ -> }
                .show()

            prefs!!.edit().putBoolean("firstrun", false).apply()
        }
    }
}


class NotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.i("sttas", "received")
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = getNotification("test azaza beset", context)
        val id = 0
        notificationManager.notify(id, notification)
    }

    private fun getNotification(content: String, context: Context): Notification? {
        val builder = NotificationCompat.Builder(context)
        builder.setContentTitle("Scheduled Notification")
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        return builder.build()
    }

    companion object {
        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
    }
}