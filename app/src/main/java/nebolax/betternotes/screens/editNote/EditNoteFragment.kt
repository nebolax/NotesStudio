package nebolax.betternotes.screens.editNote

import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import nebolax.betternotes.R
import nebolax.betternotes.databinding.EditnoteFragmentBinding

class EditNoteFragment: Fragment() {
    private lateinit var binding: EditnoteFragmentBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textView10.background = ContextCompat.getDrawable(requireContext(), R.drawable.tag3_bg)
        val newTag = TextView(context)
        binding.tagsLayout.addView(newTag)
        newTag.background = ContextCompat.getDrawable(requireContext(), R.drawable.tag1_bg)
    }
}