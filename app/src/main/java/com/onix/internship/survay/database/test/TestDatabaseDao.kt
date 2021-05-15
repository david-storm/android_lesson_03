package com.onix.internship.survay.database.test

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TestDatabaseDao {

    @Insert
    suspend fun insert(test: Test)

    @Update
    suspend fun update(test: Test)

    @Query("SELECT * FROM tests ORDER BY id DESC")
    suspend fun testAll() : List<Test>

}