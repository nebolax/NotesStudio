package nebolax.betternotes.screens.notesObserver

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView

import androidx.core.content.ContextCompat
import nebolax.betternotes.R
import nebolax.betternotes.notes.AlexNote
import nebolax.betternotes.notes.NotesManager
import kotlin.math.abs

@SuppressLint("ClickableViewAccessibility", "SetTextI18n")
@Suppress("JoinDeclarationAndAssignment")
class NoteTitlePart(
    context: Context,
    private val parentLayout: LinearLayout,
    private val note: AlexNote,
    private val parent: NotesObserverFragment
) {
    private val box: LinearLayout
    private val title: TextView
    private val timeStartView: TextView
    private val timeFinishView: TextView
    private var swipeX = 0f
    private var swipeY = 0f

    init {
        box = LinearLayout(context).apply {
            parentLayout.addView(this)
            orientation = LinearLayout.VERTICAL
            background = ContextCompat.getDrawable(context, R.drawable.note_border_shape)
            setPadding(20, 10, 0, 10)
            (layoutParams as LinearLayout.LayoutParams).bottomMargin = 20
        }

        title = TextView(context).apply {
            box.addView(this)
            setTextColor(ContextCompat.getColor(context, R.color.note_title))
            textSize = 28f
            text = note.title
        }

        timeStartView = TextView(context).apply {
            box.addView(this)
            setTextColor(ContextCompat.getColor(context, R.color.time_color))
            textSize = 19f
            text = "Start: ${note.startTime.allString}"
        }

        timeFinishView = TextView(context).apply {
            box.addView(this)
            setTextColor(ContextCompat.getColor(context, R.color.time_color))
            textSize = 19f
            text = "End: ${note.endTime.allString}"
        }

        box.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                swipeX = event.x
                swipeY = event.y
            } else if (event.action == MotionEvent.ACTION_UP) {
                if (abs(event.y - swipeY) < 60f) {
                    if (swipeX - event.x >= 100f) {
                        parentLayout.removeView(box)
                        Log.i("removvver", "doing")
                        NotesManager.deleteNote(note)
                    } else if (abs(event.x - swipeX) < 10) {
                        parent.navigate(note)
                    }
                }
            }
            true
        }
    }
}