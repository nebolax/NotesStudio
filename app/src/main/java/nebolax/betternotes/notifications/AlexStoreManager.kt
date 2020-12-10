package nebolax.betternotes.notifications

import android.content.Context
import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nebolax.betternotes.AlexLogs
import java.io.File

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
            config = Config(0, 0)
            confFile.writeText(Json.encodeToString(config))
        } else {
            config = Json.decodeFromString(confFile.readText())
        }
        Log.i("NotStore", "Quantity of notifications: $config")

        if (showCur) {
            Log.i("NotStore", "Found notifications:")

        }
        AlexLogs.makeLog("Store manager has been set up")
        AlexLogs.makeLog("Current notifications in list = ${config.muchPublished}")
        NotifiesModerator.setNotifiesMy(loadNotifies())
        NotifiesModerator.setMyCurId(config.id    )
    }

    fun loadNotifies(): MutableList<AlexNotification> {
        val notifies = mutableListOf<AlexNotification>()
        cd.listFiles()?.forEach {
            Log.i("NotStore", it.path)
        }
        (0 until config.muchPublished).forEach {
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
        AlexLogs.makeLog("Current notifications dir has been cleared")
    }

    fun removeNotify(notify: AlexNotification) {
        val notifies = loadNotifies()
        var wasDetected = false
        (0 until notifies.size).forEach {
            val comp = loadNotification(it)
            if (wasDetected) {
                File(cd, (it-1).toString()).writeText(File(cd, it.toString()).readText())
                File(cd, it.toString()).delete()
            } else if (comp == notify) {
                wasDetected = true
                File(cd, it.toString()).delete()
            }
        }
        decreaseCount()
        AlexLogs.makeLog("Successfully removed notify from store")
        AlexLogs.makeLog("Current notifies list: ${loadNotifies()}")
    }

    private fun loadNotification(id: Int): AlexNotification {
        return Json.decodeFromString(File(cd, id.toString()).readText()) }

    fun addNotification(notif: AlexNotification) {
        File(cd, config.muchPublished.toString()).writeText(Json.encodeToString(notif))
        increaseCount()
        Log.i("NotStore", "Saved notification: $notif")
        AlexLogs.makeLog("Added notify to store: $notif")
    }

    private fun increaseCount() {
        confFile.delete()
        config.muchPublished++
        config.id++
        confFile.writeText(Json.encodeToString(config))
        Log.i("NotStore", "Increased: ${config.muchPublished}")
        AlexLogs.makeLog("Increased notifies count, cur = ${config.muchPublished}")
    }

    private fun decreaseCount() {
        confFile.delete()
        config.muchPublished--
        confFile.writeText(Json.encodeToString(config))
        Log.i("NotStore", "Decreased: ${config.muchPublished}")
        AlexLogs.makeLog("Decreased notifies count, cur = ${config.muchPublished}")
    }
}