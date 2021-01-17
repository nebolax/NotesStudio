package nebolax.betternotes.notes

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nebolax.betternotes.notifications.AlexNotification
import nebolax.betternotes.notifications.IdMaker
import nebolax.betternotes.notifications.NotifiesManager
import nebolax.betternotes.notifications.database.NotifiesDatabase
import java.io.File
import java.util.*

object NotesManager {
    private lateinit var context: Context
    private val dirPath = "notes_1.0/"
    private lateinit var dirFile: File
    private lateinit var config: NotesConfig
    private val notesList = mutableListOf<AlexNote>()

    fun setup(context: Context) {
        this.context = context
        dirFile = File(context.filesDir, dirPath)
        config = loadConfig()
        val dFile = File(context.filesDir, dirPath)
        if (!dFile.exists()) {
            dFile.mkdir()
        }

        loadAllNotes()
    }

    private fun loadConfig(): NotesConfig {
        val res: NotesConfig
        val f = File(dirFile, "config.txt")
        res = if (f.isFile) {
            Json.decodeFromString(f.readText())
        } else {
            NotesConfig()
        }

        return res
    }

    private fun updateConfig() {
        val f = File(dirFile, "config.txt")
        if (f.isFile) {
            f.delete()        }
        f.writeText(Json.encodeToString(config))
    }

    fun saveNote(note: AlexNote) {
        val curPath = dirPath + note.id.toString()
        val f = File(context.filesDir, curPath)
        if (f.exists()) {
            f.delete()
        }
        f.writeText(Json.encodeToString(note))

        val notifiesManager = NotifiesManager.getInstance(context, NotifiesDatabase.getInstance(context))

        Log.i("AlexIdsaver", "Start: ${note.startNotifyId}")
        Log.i("AlexIdsaver", "End: ${note.endNotifyId}")

        notifiesManager.addNotification(AlexNotification("Started: ${note.title}", note.startTime.toCalendar(), note.startNotifyId))
        notifiesManager.addNotification(AlexNotification("Finished: ${note.title}", note.endTime.toCalendar(), note.endNotifyId))
    }

    fun loadAllNotes(): List<AlexNote> {
        notesList.clear()
        config.existingNotesIds.forEach {
            notesList.add(loadNote(it))
        }
        return notesList
    }

    fun loadNote(id: Int): AlexNote {
        val curPath = dirPath + id.toString()
        val f = File(context.filesDir, curPath)
        val res: AlexNote
        res = if (f.exists()) {
            Json.decodeFromString(f.readText())
        } else {
            createNewNote(title = "Note is unavailable")
        }
        return res
    }

    fun createNewNote(text: String = "", title: String = ""): AlexNote {
        Log.i("clclc", "from man")
        val idMaker = IdMaker.getInstance(
            context.getSharedPreferences("nebolax.betternotes",
                AppCompatActivity.MODE_PRIVATE))

        val res = AlexNote(id = config.lastId, body = text, title = title, startNotifyId = idMaker.getNext(), endNotifyId = idMaker.getNext())
        notesList.add(res)
        config.existingNotesIds.add(config.lastId)
        saveNote(res)
        config.lastId++
        updateConfig()
        return res
    }

    fun deleteNote(note: AlexNote) {
        notesList.remove(note)
        config.existingNotesIds.remove(note.id)
        updateConfig()
        File(dirFile, note.id.toString()).delete()
    }
}