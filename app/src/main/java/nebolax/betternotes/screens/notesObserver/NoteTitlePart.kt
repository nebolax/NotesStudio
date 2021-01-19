package nebolax.betternotes.screens.notesObserver

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.app.DialogCompat
import androidx.core.content.ContextCompat
import androidx.core.view.marginRight
import kotlinx.android.synthetic.main.editnote_fragment.view.*
import kotlinx.android.synthetic.main.note_title.view.*
import nebolax.betternotes.GlobalVars
import nebolax.betternotes.R
import nebolax.betternotes.notes.AlexNote
import nebolax.betternotes.notes.NotesManager
import kotlin.math.abs


@SuppressLint("ClickableViewAccessibility", "SetTextI18n")
@Suppress("JoinDeclarationAndAssignment")
class NoteTitlePart(
    context: Context,
    private val parentLayout: LinearLayout,
    private val scrollView: ScrollView,
    private val note: AlexNote,
    private val parent: NotesObserverFragment,
): View(context) {
    private var swipeX = 0f
    private var swipeY = 0f
    val nhm: View

    init {
        nhm = parent.layoutInflater.inflate(R.layout.note_title, parentLayout, false)

        nhm.title_note_text.apply {
            text = if (note.title.isNotBlank()) {
                setTextColor(ContextCompat.getColor(context, R.color.note_title))
                note.title
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.empty_note_title))
                val s = note.body.replace("\n", "")
                if (s.isNotBlank()) {
                    "[#noTitle] $s"
                } else {
                    "Empty note"
                }
            }
            maxLines = 1
        }

        nhm.note_start_text.apply {
            text = if (note.startTimeSet) {
                "Start: ${note.startTime.allString}"
            } else {
                "Start time isn't set"
            }
        }

        nhm.note_end_text.apply {
            text = if (note.endTimeSet) {
                "End: ${note.endTime.allString}"
            } else {
                "End time isn't set"
            }
        }

        nhm.date_lin_layout.setOnClickListener {
            Log.i("AlexClickk", "navreq")
            parent.navigate(note)
        }

        nhm.delete_note_btn.apply {
            setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Are you sure?")
                    .setMessage("Are you sure you want to delete note \"${note.title}\"?")
                    .setPositiveButton("Yes") { _, _ ->
                        parentLayout.removeView(nhm)
                        NotesManager.deleteNote(note)
                    }
                    .setNegativeButton("No") { _, _ -> }
                    .show()
            }
        }

        parentLayout.addView(nhm, 0)
    }
}