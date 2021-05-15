package com.onix.internship.survay.database.access

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AccessDatabaseDao {

    @Insert
    suspend fun insert(access: Access)

    @Delete
    suspend fun delete(access: Access)

    @Query("SELECT * FROM access WHERE uid = :uid AND testId = :testId LIMIT 1")
    suspend fun find(uid: Int, testId: Int) : List<Access>
}