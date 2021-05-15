package com.onix.internship.survay.database.result

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ResultDatabaseDao {

    @Insert
    suspend fun insert(result: Result)

    @Update
    suspend fun update(result: Result)

    @Query("SELECT * FROM results")
    suspend fun resultsAll() : List<Result>

}