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
        var sday = day.toString()
        if (sday.length == 1) sday = "0$sday"
        var smonth = (month+1).toString()
        if (smonth.length == 1) smonth = "0$smonth"
        val s = "$sday.$smonth.$year"
        emit(UpdateChoosenDate(s))
    }

    fun timePicked(hours: Int, minutes: Int) {
        Log.i("Notifier", "Picked - hours: $hours, minutes: $minutes")
        dateTime.hours = hours
        dateTime.minutes = minutes
        var shours = hours.toString()
        if (shours.length == 1) shours = "0$shours"
        var smins = minutes.toString()
        if (smins.length == 1) smins = "0$smins"
        val s = "$shours:$smins"
        emit(UpdateChoosenTime(s))
    }
}