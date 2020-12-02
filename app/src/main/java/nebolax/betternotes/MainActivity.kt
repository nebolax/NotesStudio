package nebolax.betternotes

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import events.ServCheck
import events.emit
import nebolax.betternotes.notifications.NotifiesModerator
import nebolax.betternotes.notifications.NotifyService


class MainActivity : AppCompatActivity() {
    private var prefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("AlexActivity", "Main started")

        val intent = Intent(this, NotifyService::class.java)
        startService(intent)
        prefs = getSharedPreferences("nebolax.betternotes", MODE_PRIVATE)
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onResume() {
        super.onResume()
        if (prefs!!.getBoolean("firstrun", true)) {
            Log.i("AlexFOpen", "Trying to open security app")
            setupChannel()
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
                            intent.putExtra("packageName", BuildConfig.APPLICATION_ID) }
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

            prefs!!.edit().putBoolean("firstrun", false).apply();
        } else {
            Log.i("AlexFOpen", "It's not first run")
        }
    }

    private fun setupChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "no description azazaza"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NotifiesModerator.chid, "Huh dude you are dude", importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}