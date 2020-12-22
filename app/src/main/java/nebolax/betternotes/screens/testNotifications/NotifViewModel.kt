package nebolax.betternotes.screens.testNotifications

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import events.*
import nebolax.betternotes.notifications.AlexNotification
import nebolax.betternotes.notifications.TimeStruct
import nebolax.betternotes.notifications.NotifiesModerator

class NotifViewModel(private val app: Application) : AndroidViewModel(app) {
    private var message = ""
    private val dateTime = TimeStruct()

    init {
        register<SetMessage> {
            message = it.message
        }
    }

    fun clearNotifies() {
        NotifiesModerator.removeAllNotify()
    }

    fun addNotification() {
        emit(RequestMessage())
        NotifiesModerator.addNotify(AlexNotification(dateTime, message))
        emit(NotificationAdded())
    }

    fun datePicked(year: Int, month: Int, day: Int) {
        Log.i("Notifier", "Picked - year: $year, month: $month, day: $day")
        dateTime.year = year
        dateTime.month = month
        dateTime.day = day
        emit(UpdateChoosenDate(dateTime.dateString()))
    }

    fun timePicked(hours: Int, minutes: Int) {
        Log.i("Notifier", "Picked - hours: $hours, minutes: $minutes")
        dateTime.hours = hours
        dateTime.minutes = minutes
        emit(UpdateChoosenTime(dateTime.timeString()))
    }
}