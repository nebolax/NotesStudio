package nebolax.betternotes.screens.notesObserver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val notes = NotesManager.loadAllNotes()
        Log.i("llll", notes.size.toString())
        notes.forEach {
            NoteTitlePart(requireContext(), binding.notesHandler, it, this)
        }
    }

    fun navigate(note: AlexNote) {
        binding.notesHandler.removeAllViews()
        findNavController().navigate(
            R.id.action_notesObserverFragment_to_editNoteFragment,
            bundleOf("note" to Json.encodeToString(note))
        )
    }


    private fun createNewNote() {
        Log.i("clclc", "creating")
        val newNote = NotesManager.createNewNote(title = "Empty note")
        val ff = NoteTitlePart(requireContext(), binding.notesHandler, newNote, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("status", "destroyed view")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("status", "detached")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("status", "destroyed")
    }
}