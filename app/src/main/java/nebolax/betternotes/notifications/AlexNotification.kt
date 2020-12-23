package nebolax.betternotes.notifications

import nebolax.betternotes.notifications.database.DatabaseNotification
import java.util.*

data class AlexNotification(
    val message: String = "",
    val timeToCall: Calendar = Calendar.getInstance(),
    val id: Int = 0
) {
    fun toDatabaseNotify(): DatabaseNotification {
        return DatabaseNotification(notifyId = id, message = message, callTimeMillis = timeToCall.time.time)
    }
}