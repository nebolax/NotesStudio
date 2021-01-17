package nebolax.betternotes.screens.editNote

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import nebolax.betternotes.notes.TimeStruct

@SuppressLint("SetTextI18n")
@BindingAdapter("startDateTime")
fun TextView.setStartDateTime(time: TimeStruct?) {
    Log.i("AlexAdapters","Start Adapter called")
    time?.let {
        text = "Start: ${time.allString}"
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("endDateTime")
fun TextView.setEndDateTime(time: TimeStruct?) {
    time?.let {
        text = "End: ${time.allString}"
    }
}