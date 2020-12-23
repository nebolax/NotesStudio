package nebolax.betternotes.screens.testNotifications

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nebolax.betternotes.notifications.NotifiesManager
import nebolax.betternotes.notifications.database.NotifiesDatabaseDao

class NotifViewModelFactory(
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotifViewModel::class.java)) {
            return NotifViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}