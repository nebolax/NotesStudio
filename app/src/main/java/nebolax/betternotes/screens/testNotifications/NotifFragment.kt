package nebolax.betternotes.screens.testNotifications

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import events.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nebolax.betternotes.R
import nebolax.betternotes.databinding.NotifFragmentBinding
import nebolax.betternotes.notifications.NotifiesModerator
import java.lang.Thread.sleep

class NotifFragment: Fragment() {
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
        viewModel = ViewModelProvider(this).get(NotifViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        mainSetup()
        return binding.root
    }

    private fun mainSetup() {
        binding.pickDate.setOnClickListener { pickDate() }
        binding.pickTime.setOnClickListener { pickTime() }

        register<UpdateChoosenDate> { binding.curDate.text = it.date }
        register<UpdateChoosenTime> { binding.curTime.text = it.time }
        register<NotificationAdded> {
            Toast.makeText(this.context, "Notification has been created", Toast.LENGTH_SHORT).show()
        }
        register<RequestMessage> {
            emit(SetMessage(binding.messageEdit.text.toString()))
        }
    }

    private fun pickTime() {
        val dialog = TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hours, minutes ->
            viewModel.timePicked(hours, minutes)
        }, 0, 0, true)
        dialog.show()
    }

    private fun pickDate() {
        val dialog = DatePickerDialog(this.requireContext())
        dialog.setOnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.datePicked(year, month, dayOfMonth)
        }
        dialog.show()
    }
}