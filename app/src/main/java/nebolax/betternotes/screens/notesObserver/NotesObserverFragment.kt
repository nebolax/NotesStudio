package nebolax.betternotes.screens.notesObserver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import nebolax.betternotes.R
import nebolax.betternotes.databinding.NotesObserverFragmentBinding

class NotesObserverFragment: Fragment() {
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
        return binding.root
    }
}