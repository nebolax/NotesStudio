package nebolax.betternotes.screens.testNotifications

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

@SuppressLint("SetTextI18n")
@BindingAdapter("date")
fun TextView.setDate(calendar: Calendar?) {
    calendar?.let {
        val year = calendar.get(Calendar.YEAR).toString()
        val monthBuf = (calendar.get(Calendar.MONTH)+1).toString()
        val month =
            if (monthBuf.length == 2) {
                monthBuf
            } else {
                "0$monthBuf"
            }
        val dayBuf = calendar.get(Calendar.DAY_OF_MONTH).toString()
        val day =
            if (dayBuf.length == 2) {
                dayBuf
            } else {
                "0$dayBuf"
            }

        text = "$day.$month.$year"
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("time")
fun TextView.setTime(calendar: Calendar?) {
    calendar?.let {
        val hoursBuf = calendar.get(Calendar.HOUR_OF_DAY).toString()
        val hours =
            if (hoursBuf.length == 2) {
                hoursBuf
            } else {
                "0$hoursBuf"
            }
        val minutesBuf = calendar.get(Calendar.MINUTE).toString()
        val minutes =
            if (minutesBuf.length == 2) {
                minutesBuf
            } else {
                "0$minutesBuf"
            }

        text = "$hours:$minutes"
    }
}