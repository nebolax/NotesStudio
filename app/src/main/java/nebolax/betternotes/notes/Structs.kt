package nebolax.betternotes.notes

import android.view.View
import kotlinx.serialization.Serializable

@Serializable
data class AlexNote(
    val id: Int,
    var body: String = "",
    var title: String = "",
    val startTime: TimeStruct = TimeStruct(),
    val endTime: TimeStruct = TimeStruct(),
    val tags: MutableList<AlexTag> = mutableListOf()
)

@Serializable
data class AlexTag(
    val text: String,
    val color: Int
)

@Serializable
data class NotesConfig(
    var lastId: Int = 0,
    val existingNotesIds: MutableList<Int> = mutableListOf()
)

@Serializable
data class TimeStruct