package nebolax.betternotes.screens.editNote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nebolax.betternotes.notes.AlexNote

class EditNoteViewModelFactory(
    private val application: Application,
    private val note: AlexNote
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            return EditNoteViewModel(application, note) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}