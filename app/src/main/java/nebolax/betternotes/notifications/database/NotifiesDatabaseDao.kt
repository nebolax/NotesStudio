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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotifiesDatabaseDao {
    @Insert
    suspend fun insert(notify: DatabaseNotification)

    @Update
    suspend fun update(notify: DatabaseNotification)

    @Query("SELECT * from pending_notifications_table WHERE notifyId = :key")
    suspend fun get(key: Int): DatabaseNotification?

    @Query("DELETE FROM pending_notifications_table WHERE notifyId = :key")
    suspend fun deleteNotify(key: Int)

    @Query("DELETE FROM pending_notifications_table")
    suspend fun clearAll()

    @Query("SELECT * FROM pending_notifications_table ORDER BY notifyId DESC")
    fun getAllNotifies(): List<DatabaseNotification>

    @Query("SELECT * FROM pending_notifications_table ORDER BY time_to_call_seconds DESC LIMIT 1")
    suspend fun getNearest(): DatabaseNotification?
}
