package nebolax.betternotes.screens.editNote

import android.annotation.SuppressLint
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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import nebolax.betternotes.R
import nebolax.betternotes.databinding.EditnoteFragmentBinding
import nebolax.betternotes.notes.AlexNote
import nebolax.betternotes.notes.NotesManager

class EditNoteFragment: Fragment() {
    private lateinit var binding: EditnoteFragmentBinding
    private lateinit var curNote: AlexNote
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
        binding.mainText.addTextChangedListener {
            curNote.body = binding.mainText.text.toString()
            NotesManager.saveNote(curNote) }
        binding.titleText.addTextChangedListener {
            curNote.title = binding.titleText.text.toString()
            NotesManager.saveNote(curNote) }

        //binding.mainText.clipBounds = Rect(0, 0, 200, 200)
        binding.mainText.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.mainText.clipToOutline = true
       // binding.mainText.
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
        curNote = Json.decodeFromString(requireArguments().getString("note").toString())
        Log.i("ffrom", curNote.title)
        binding.titleText.setText(curNote.title)
        binding.mainText.setText(curNote.body)
        binding.dateStartView.setText("Start: ${curNote.startTime.allString()}")
        binding.dateEndView.setText("End: ${curNote.endTime.allString()}")
    }

    fun getTagColor(id: Int): Int {
        when (id) {
            1 -> return R.drawable.tag1_bg
            2 -> return R.drawable.tag2_bg
            3 -> return R.drawable.tag3_bg
        }
        return R.drawable.tag1_bg
    }

    fun getTagText(id: Int): Int {
        when (id) {
            1 -> return R.string.tag1_text
            2 -> return R.string.tag2_text
            3 -> return R.string.tag3_text
        }
        return R.string.tag1_text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("status", "destroyed view 2")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("status", "detached 2")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("status", "destroyed 2")
    }
}