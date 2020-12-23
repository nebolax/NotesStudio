/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nebolax.betternotes.notifications.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nebolax.betternotes.notifications.AlexNotification
import java.util.*

@Serializable
@Entity(tableName = "pending_notifications_table")
data class DatabaseNotification(
        @PrimaryKey(autoGenerate = true)
        val notifyId: Int = 0,

        @ColumnInfo(name = "message")
        val message: String = "",

        @ColumnInfo(name = "time_to_call_seconds")
        val callTimeMillis: Long = 0
) {
        fun toAlexNotification(): AlexNotification {
                return AlexNotification(message = message, timeToCall = Calendar.getInstance().apply {time = Date(callTimeMillis)})
        }

        val jsoned: String
                get() = Json.encodeToString(this)

        companion object {
                fun fromJsoned(jsoned: String): DatabaseNotification {
                        return Json.decodeFromString(jsoned)
                }
        }
}