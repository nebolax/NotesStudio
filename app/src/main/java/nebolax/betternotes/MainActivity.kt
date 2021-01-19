package nebolax.betternotes

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nebolax.betternotes.notes.NotesManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NotesManager.setup(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("nebolax.betternotes", MODE_PRIVATE)
        if (prefs.getBoolean("isFirstRun", true)) {
            regChannel()
            requestAutostart()
            prefs.edit().putBoolean("isFirstRun", false).apply()
        }
    }

    private fun regChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "main_channel"
            val name = "main_channel"
            val description = "This is my channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channelId, name, importance)
            mChannel.description = description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(500, 500, 500, 500, 500)
            mChannel.setShowBadge(false)
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                mChannel
            )
        }
    }

    private fun requestAutostart() {
        val manufacturer = Build.MANUFACTURER
        val manufRequest = arrayOf("xiaomi", "Xiaomi", "oppo", "Oppo", "vivo", "Vivo", "letv", "Letv", "honor", "Honor", "meizu", "Meizu")
        if (manufacturer in manufRequest) {
            AlertDialog.Builder(this)
                .setTitle("Allow autostart")
                .setMessage("Please allow autostart for better experience with notifications")
                .setPositiveButton("Yes") { _, _ ->
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
                        val list =
                            packageManager.queryIntentActivities(
                                intent,
                                PackageManager.MATCH_DEFAULT_ONLY
                            )
                        if (list.size > 0) {
                            startActivity(intent)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                .setNegativeButton("No") { _, _ -> }
                .show()
        }
    }
}