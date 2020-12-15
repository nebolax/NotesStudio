package nebolax.betternotes.notes

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class NotesManager(private val context: Context) {
    private val dirPath = "notes_1.0/"
    private val dirFile: File
    private val config: NotesConfig
    val notesList = mutableListOf<AlexNote>()

    init {
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
        if (f.isFile) {
            res = Json.decodeFromString(f.readText())
        } else {
            res = NotesConfig()
        }

        return res
    }

    private fun updateConfig() {
        val f = File(dirFile, "config.txt")
        if (f.isFile) {
            f.delete()
        }
        f.writeText(Json.encodeToString(config))
    }

    fun saveNote(note: AlexNote) {
        val curPath = dirPath + note.id.toString()
        val f = File(context.filesDir, curPath)
        if (f.exists()) {
            f.delete()
        }
        f.writeText(Json.encodeToString(note))
    }

    fun loadAllNotes() {
        config.existingNotesIds.forEach {
            val f = File(dirFile, it.toString())
            val note: AlexNote = Json.decodeFromString(f.readText())
            notesList.add(note)
        }
    }

    fun loadNote(id: Int): AlexNote {
        val curPath = dirPath + id.toString()
        val f = File(context.filesDir, curPath)
        val res: AlexNote
        res = if (f.exists()) {
            Json.decodeFromString(f.readText())
        } else {
            createNewNote("Note is unavailable")
        }
        return res
    }

    fun createNewNote(text: String = ""): AlexNote {
        val res = AlexNote(id = config.lastId, body = text)
        notesList.add(res)
        config.existingNotesIds.add(config.lastId)
        config.lastId++
        updateConfig()
        return res
    }
}