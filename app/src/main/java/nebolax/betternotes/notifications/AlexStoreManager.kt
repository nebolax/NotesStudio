package nebolax.betternotes.notifications

import android.content.Context
import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.*

object AlexStoreManager {
    private val dirForStore = "notif_test1.0"
    private lateinit var cont: Context
    private lateinit var cd: File
    private lateinit var config: Config
    private lateinit var confFile: File

    fun setup(context: Context, showCur: Boolean = true) {
        this.cont = context
        cd = File(cont.filesDir, dirForStore)
        confFile = File(cd, "config.txt")
        if (!cd.exists()) {
            cd.mkdir()
            config = Config(0)
            confFile.writeText(Json.encodeToString(config))
        } else {
            config = Json.decodeFromString(confFile.readText())
        }
        Log.i("NotStore", "Quantity of notifications: $config")

        if (showCur) {
            Log.i("NotStore", "Found notifications:")

        }
        NotifiesModerator.setNotifiesMy(loadNotifies())
    }

    fun loadNotifies(): MutableList<AlexNotification> {
        val notifies = mutableListOf<AlexNotification>()
        cd.listFiles()?.forEach {
            Log.i("NotStore", it.path)
        }
        (0 until config.muchNotifsExist).forEach {
            val notify = loadNotification(it)
            notifies.add(notify)
            Log.i("NotStore", notify.toString())
        }
        return notifies
    }

    fun clearCurDir() {
        cd.listFiles()?.forEach {
            it.delete()
        }
        cd.delete()
        setup(cont)
    }

    fun removeNotify(notify: AlexNotification) {
        val notifies = loadNotifies()
        var wasDetected = false
        (0 until notifies.size).forEach {
            val comp = loadNotification(it)
            if (wasDetected) {
                File(cd, (it-1).toString()).delete()
                File(cd, (it-1).toString()).writeText(File(cd, it.toString()).readText())
            } else if (comp == notify) {
                wasDetected = true
            }
        }
        decreaseCount()
    }

    private fun loadNotification(id: Int): AlexNotification {
        return Json.decodeFromString(File(cd, id.toString()).readText()) }

    fun addNotification(notif: AlexNotification) {
        File(cd, config.muchNotifsExist.toString()).writeText(Json.encodeToString(notif))
        increaseCount()
        Log.i("NotStore", "Saved notification: $notif")
    }

    private fun increaseCount() {
        confFile.delete()
        config.muchNotifsExist++
        confFile.writeText(Json.encodeToString(config))
        Log.i("NotStore", "Increased: ${config.muchNotifsExist}")
    }

    private fun decreaseCount() {
        confFile.delete()
        config.muchNotifsExist--
        confFile.writeText(Json.encodeToString(config))
        Log.i("NotStore", "Decreased: ${config.muchNotifsExist}")
    }
}