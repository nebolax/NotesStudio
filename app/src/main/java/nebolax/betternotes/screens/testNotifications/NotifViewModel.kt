package nebolax.betternotes.screens.testNotifications


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

class NotifViewModel(private val app: Application) : AndroidViewModel(app)
{

    private val _selectedDateTime: MutableLiveData<Calendar> = MutableLiveData(Calendar.getInstance())
    val selectedDateTime: LiveData<Calendar>
        get() = _selectedDateTime

    private val _createNotifyRequest = MutableLiveData(false)
    val createNotifyRequest: LiveData<Boolean>
        get() = _createNotifyRequest

    private val _clearNotifiesRequest = MutableLiveData(false)
    val clearNotifiesRequest: LiveData<Boolean>
        get() = _clearNotifiesRequest

    private val _datePickerRequest = MutableLiveData(false)
    val datePickerRequest: LiveData<Boolean>
        get() = _datePickerRequest

    private val _timePickerRequest = MutableLiveData(false)
    val timePickerRequest: LiveData<Boolean>
        get() = _timePickerRequest

    init {
        _selectedDateTime.value?.add(Calendar.MINUTE, 1)
        _selectedDateTime.value?.set(Calendar.SECOND, 0)
    }

    fun clearNotifies() {
        _clearNotifiesRequest.value = true
    }

    fun addNotification() {
        _createNotifyRequest.value = true
    }

    fun pickDate() {
        _datePickerRequest.value = true
    }

    fun pickTime() {
        _timePickerRequest.value = true
    }

    fun notifiesCleared() {
        _clearNotifiesRequest.value = false
    }

    fun datePicked(year: Int, month: Int, day: Int) {
        val newcal = selectedDateTime.value!!.clone() as Calendar
        newcal.set(Calendar.YEAR, year)
        newcal.set(Calendar.MONTH, month)
        newcal.set(Calendar.DAY_OF_MONTH, day)
        _selectedDateTime.value = newcal
    }

    fun timePicked(hours: Int, minutes: Int) {
        val newcal = selectedDateTime.value!!.clone() as Calendar
        newcal.set(Calendar.HOUR_OF_DAY, hours)
        newcal.set(Calendar.MINUTE, minutes)
        _selectedDateTime.value = newcal
    }
}