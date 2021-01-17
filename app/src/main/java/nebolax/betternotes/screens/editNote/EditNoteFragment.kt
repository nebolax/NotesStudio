package nebolax.betternotes.screens.editNote

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import nebolax.betternotes.R
import nebolax.betternotes.databinding.EditnoteFragmentBinding
import nebolax.betternotes.notes.AlexNote
import nebolax.betternotes.notes.NotesManager
import nebolax.betternotes.screens.testNotifications.NotifViewModel
import nebolax.betternotes.screens.testNotifications.NotifViewModelFactory
import java.util.*

class EditNoteFragment: Fragment() {
    private lateinit var binding: EditnoteFragmentBinding
    private lateinit var viewModel: EditNoteViewModel
    private lateinit var curNote: AlexNote

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.editnote_fragment,
            container,
            false
        )
        curNote = Json.decodeFromString(requireArguments().getString("note").toString())

        Log.i("AlexNoteStatus", curNote.toString())

        val factory = EditNoteViewModelFactory(requireNotNull(activity).application, curNote)
        viewModel = ViewModelProvider(this, factory).get(EditNoteViewModel::class.java)

        viewModel.curStartTime.observe(viewLifecycleOwner) {
            Log.i("AlexAdapters", "New start: $it")
            binding.dateStartView.text = "Start: ${it.allString}"
        }
        viewModel.curEndTime.observe(viewLifecycleOwner) {
            Log.i("AlexAdapters", "New end: $it")
            binding.dateEndView.text = "End: ${it.allString}"
        }

        binding.viewModel = viewModel
        binding.mainText.addTextChangedListener {
            curNote.body = binding.mainText.text.toString()
            NotesManager.saveNote(curNote) }
        binding.titleText.addTextChangedListener {
            curNote.title = binding.titleText.text.toString()
            NotesManager.saveNote(curNote) }

        binding.dateStartView.setOnClickListener { setNewDateTime(TimeType.Start) }
        binding.dateEndView.setOnClickListener { setNewDateTime(TimeType.End) }

        binding.mainText.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.mainText.clipToOutline = true
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        for(i in 0 until 3) {
            val newTag = TextView(context)
            binding.tagsLayout.addView(newTag)
            newTag.setTextColor(ContextCompat.getColor(requireContext(), R.color.editor_background))
            newTag.setPadding(5, 1, 5, 1)
            (newTag.layoutParams as LinearLayout.LayoutParams).bottomMargin = 5
            newTag.background = ContextCompat.getDrawable(requireContext(), getTagColor(i+1))
            newTag.text = resources.getString(getTagText(i+1))
        }
        Log.i("ffrom", curNote.title)
        binding.titleText.setText(curNote.title)
        binding.mainText.setText(curNote.body)
    }

    private fun setNewDateTime(type: TimeType) {
        val dialog = DatePickerDialog(requireContext())
        val curCal = if (type == TimeType.Start) viewModel.curStartTime
                    else viewModel.curEndTime
        dialog.setOnDateSetListener { _, year, month, day ->
            TimePickerDialog(
                requireContext(), { _, hours, minutes ->
                    viewModel.dateAndTimePicked(type, year, month, day, hours, minutes)
                }, curCal.value!!.hours,
                curCal.value!!.minutes,
                true
            ).show()
        }
        dialog.show()
    }

    private fun getTagColor(id: Int): Int {
        when (id) {
            1 -> return R.drawable.tag1_bg
            2 -> return R.drawable.tag2_bg
            3 -> return R.drawable.tag3_bg
        }
        return R.drawable.tag1_bg
    }

    private fun getTagText(id: Int): Int {
        when (id) {
            1 -> return R.string.tag1_text
            2 -> return R.string.tag2_text
            3 -> return R.string.tag3_text
        }
        return R.string.tag1_text
    }
}