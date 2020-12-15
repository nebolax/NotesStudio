package nebolax.betternotes.screens.notesObserver

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import nebolax.betternotes.R

@Suppress("JoinDeclarationAndAssignment")
class NoteTitlePart(
    private val context: Context,
    private val parentLayout: ConstraintLayout
) {
    private val boxLayout: View
    init {
        boxLayout = ConstraintLayout(context)
        parentLayout.addView(boxLayout)
        boxLayout.background = ContextCompat.getDrawable(context, R.drawable.note_border_shape)

        val set = ConstraintSet()
        set.clone(parentLayout)

        set.applyTo(parentLayout)
    }
}