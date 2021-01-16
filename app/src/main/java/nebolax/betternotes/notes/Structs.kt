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
data class TimeStruct(
    var year: Int=0,
    var month: Int=0,
    var day: Int=0,
    var hours: Int=0,
    var minutes: Int=0
) {
    fun timeString(): String {
        var shours = hours.toString()
        if (shours.length == 1) shours = "0$shours"
        var smins = minutes.toString()
        if (smins.length == 1) smins = "0$smins"
        return "$shours:$smins"
    }

    fun dateString(): String {
        var sday = day.toString()
        if (sday.length == 1) sday = "0$sday"
        var smonth = (month+1).toString()
        if (smonth.length == 1) smonth = "0$smonth"
        var pyear = (year % 100).toString()
        if (pyear.length == 1) pyear = "0$pyear"
        return "$sday.$smonth.$pyear"
    }

    fun allString(): String {
        return "${dateString()}, ${timeString()}"
    }
}