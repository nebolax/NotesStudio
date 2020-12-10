package nebolax.betternotes.notifications

import kotlinx.serialization.*

@Serializable
data class AlexNotification(
    var date: DateStruct,
    var message: String,
)

@Serializable
data class DateStruct(
    var year: Int=0,
    var month: Int=0,
    var day: Int=0,
    var hours: Int=0,
    var minutes: Int=0
)

@Serializable
data class Config(
    var muchPublished: Int,
    var id: Int
)