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