package nebolax.betternotes

import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.util.*

object AlexLogs {
    private val name = "logs.txt"
    private lateinit var cont: Context
    private var dir = ""

    fun setup(context: Context) {
        cont = context
        dir = cont.filesDir.path
        Log.i("ddir", dir)
    }

    fun makeLog(message: String) {
        val f = File(dir, name)
        if (!f.isFile) {
            f.createNewFile()
        }
        val cal = Calendar.getInstance()
        f.appendText("${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}:${cal.get(Calendar.SECOND)}- $message\n")
    }

    fun getAllLogs(): String {
        makeLog("Logs requested")
        return File(dir, name).readText()
    }

    fun clearAllLogs() {
        File(dir, name).delete()
    }
}