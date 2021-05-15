package com.onix.internship.survay.database.answer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AnswerDatabaseDao {

    @Insert
    suspend fun insert(answer: Answer)

    @Update
    suspend fun update(answer: Answer)

    @Query("SELECT * FROM answers")
    suspend fun answersAll() : List<Answer>
}