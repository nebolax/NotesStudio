package nebolax.betternotes.screens.notesObserver

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.notes_observer_fragment.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nebolax.betternotes.GlobalVars
import nebolax.betternotes.R
import nebolax.betternotes.databinding.NotesObserverFragmentBinding
import nebolax.betternotes.notes.AlexNote
import nebolax.betternotes.notes.NotesManager


class NotesObserverFragment : Fragment() {
    private lateinit var binding: NotesObserverFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.notes_observer_fragment,
            container,
            false
        )
        Log.i("status", "created")
        binding.newNoteBtn.setOnClickListener {
            Log.i("clclc", "clicked")
            createNewNote() }

        binding.testBtn.setOnClickListener {
            binding.scrollView.scrollTo(0, GlobalVars.lastYScroll)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val notes = NotesManager.loadAllNotes()
        Log.i("llll", notes.size.toString())

        notes.forEach {
            NoteTitlePart(requireContext(), binding.notesHandler, binding.scrollView, it, this)
        }
        binding.scrollView.scrollTo(0, GlobalVars.lastYScroll)

        val vto: ViewTreeObserver = scrollView.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                scrollView.viewTreeObserver
                    .removeOnGlobalLayoutListener(this)
                binding.scrollView.scrollTo(0, GlobalVars.lastYScroll)
            }
        })
     }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.scrollView.scrollTo(0, GlobalVars.lastYScroll)
    }

    fun navigate(note: AlexNote) {
        Log.i("AlexState", binding.scrollView.scrollY.toString())
        GlobalVars.lastYScroll = binding.scrollView.scrollY
        onSaveInstanceState(bundleOf("scrollView" to binding.scrollView.scrollY))
        binding.notesHandler.removeAllViews()
        findNavController().navigate(
            R.id.action_notesObserverFragment_to_editNoteFragment,
            bundleOf("note" to Json.encodeToString(note))
        )
    }


    private fun createNewNote() {
        Log.i("clclc", "creating")
        val newNote = NotesManager.createNewNote()
        val ff = NoteTitlePart(
            requireContext(),
            binding.notesHandler,
            binding.scrollView,
            newNote,
            this
        )
    }
}