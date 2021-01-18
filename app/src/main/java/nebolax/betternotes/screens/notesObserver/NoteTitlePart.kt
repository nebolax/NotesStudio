package nebolax.betternotes.screens.notesObserver

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.DialogCompat
import androidx.core.content.ContextCompat
import androidx.core.view.marginRight
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
    private val delNoteView: TextView
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
            if (note.title.isNotBlank()) {
                setTextColor(ContextCompat.getColor(context, R.color.note_title))
                text = note.title
            } else {
                val s = note.body.replace("\n", "")
                setTextColor(ContextCompat.getColor(context, R.color.empty_note_title))
                text = if (s.isNotBlank()) {
                    "[#noTitle] $s"
                } else {
                    "Empty note"
                }
            }
            textSize = 28f
            maxLines = 1
        }.also {
            it.setOnClickListener {
                parent.navigate(note)
            }
        }

        timeStartView = TextView(context).apply {
            box.addView(this)
            setTextColor(ContextCompat.getColor(context, R.color.time_color))
            textSize = 19f
            text = if (note.startTimeSet) {
                "Start: ${note.startTime.allString}"
            } else {
                "Start time isn't set"
            }
        }

        timeFinishView = TextView(context).apply {
            box.addView(this)
            setTextColor(ContextCompat.getColor(context, R.color.time_color))
            textSize = 19f
            text = if (note.endTimeSet) {
                "End: ${note.endTime.allString}"
            } else {
                "End time isn't set"
            }
        }

        delNoteView = TextView(context).apply {
            box.addView(this)
            textAlignment = TextView.TEXT_ALIGNMENT_VIEW_END
            val content = SpannableString("delete")
           // content.setSpan(UnderlineSpan(), 0, content.length, 0)
            val cs = object: ClickableSpan() {
                override fun onClick(widget: View) {
                    AlertDialog.Builder(context)
                        .setTitle("Are you sure?")
                        .setMessage("Are you sure you want to delete note \"${note.title}\"?")
                        .setPositiveButton("Yes") { _, _ ->
                            parentLayout.removeView(box)
                            NotesManager.deleteNote(note)
                        }
                        .setNegativeButton("No") { _, _ -> }
                        .show()
                }
            }
            content.setSpan(cs, 0, content.length, 0)

            text = content
            textSize = 18f
            setTextColor(ContextCompat.getColor(context, R.color.tag3))
            (layoutParams as LinearLayout.LayoutParams).also {
                it.marginEnd = 20
            }
        }
    }
}