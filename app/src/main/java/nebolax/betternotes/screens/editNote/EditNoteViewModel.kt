package nebolax.betternotes.screens.editNote

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import nebolax.betternotes.notes.AlexNote
import nebolax.betternotes.notes.NotesManager
import nebolax.betternotes.notes.TimeStruct

class EditNoteViewModel(
    app: Application,
    private val note: AlexNote): AndroidViewModel(app) {
    private val _curStartTime = MutableLiveData<TimeStruct>()
    val curStartTime: LiveData<TimeStruct>
        get() = _curStartTime

    private val _curEndTime = MutableLiveData<TimeStruct>()
    val curEndTime: LiveData<TimeStruct>
        get() = _curEndTime

    init {

        _curStartTime.value = note.startTime
        _curEndTime.value = note.endTime
    }

    fun dateAndTimePicked(type: TimeType, year: Int, month: Int, day: Int, hours: Int, minutes: Int) {
        val newcal = TimeStruct(
            year, month, day, hours, minutes
        )
        when (type) {
             TimeType.Start -> {
                 _curStartTime.value = newcal
                 note.startTime = newcal
             }
            else -> {
                _curEndTime.value = newcal
                note.endTime = newcal
            }
        }
        NotesManager.saveNote(note)
        Log.i("AlexAdapters", "Set new calendar")
    }
}

enum class TimeType {
    Start,
    End
}