package nebolax.betternotes.screens.testNotifications

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nebolax.betternotes.R
import nebolax.betternotes.databinding.NotifFragmentBinding
import nebolax.betternotes.notifications.AlexNotification
import nebolax.betternotes.notifications.NotifiesManager
import nebolax.betternotes.notifications.database.NotifiesDatabase
import java.util.*

class NotifFragment: Fragment() {
    private val notifFragmentScope = CoroutineScope(Dispatchers.IO)
    private lateinit var binding: NotifFragmentBinding
    private lateinit var viewModel: NotifViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.notif_fragment,
            container,
            false
        )

        val notifiesManager = NotifiesManager.getInstance(
            requireContext().applicationContext,
            NotifiesDatabase.getInstance(requireContext().applicationContext))

        val factory = NotifViewModelFactory(requireNotNull(activity).application)
        viewModel = ViewModelProvider(this, factory).get(NotifViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.curDate.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.curTime.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel.createNotifyRequest.observe(viewLifecycleOwner) {
            if (it == true) {
                notifiesManager.addNotification(AlexNotification(
                    message = binding.messageEdit.text.toString(),
                    timeToCall = viewModel.selectedDateTime.value!!
                ))
                createdNewNotify()
            }
        }

        viewModel.clearNotifiesRequest.observe(viewLifecycleOwner) {
            if (it == true) {
                notifiesManager.deleteAllPending()
                viewModel.notifiesCleared()
                clearedNotifies()
            }
        }

        viewModel.datePickerRequest.observe(viewLifecycleOwner) {
                 if(it == true) {
                     val dialog = DatePickerDialog(requireContext())
                     dialog.setOnDateSetListener { _, year, month, day ->
                        viewModel.datePicked(year, month, day)
                     }
                     dialog.show()
                 }
        }

        viewModel.timePickerRequest.observe(viewLifecycleOwner) {
            if (it == true) {
                TimePickerDialog(
                    requireContext(), { _, hours, minutes ->
                        viewModel.timePicked(hours, minutes)
                    }, viewModel.selectedDateTime.value!!.get(Calendar.HOUR_OF_DAY),
                    viewModel.selectedDateTime.value!!.get(Calendar.MINUTE),
                    true
                ).show()
            }
        }
        return binding.root
    }

    private fun createdNewNotify() {
        binding.messageEdit.text.clear()
        binding.addNotification.isEnabled = false
        Snackbar.make(binding.adderLayout, "Added notify: ${binding.messageEdit.text}", Snackbar.LENGTH_SHORT).show()
        object : CountDownTimer(3000, 3000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                binding.addNotification.isEnabled = true
            }

        }.start()
    }

    private fun clearedNotifies() {
        binding.clearNotifications.isEnabled = false
        Snackbar.make(binding.adderLayout, "All notifies cleared", Snackbar.LENGTH_SHORT).show()
        object : CountDownTimer(3000, 3000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                binding.clearNotifications.isEnabled = true
            }

        }.start()
    }
}