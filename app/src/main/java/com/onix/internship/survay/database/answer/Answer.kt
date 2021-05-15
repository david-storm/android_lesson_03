package com.onix.internship.survay.database.answer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class Answer(

    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "text") var text: String = "",
    @ColumnInfo(name = "score") var score: Float = 0f,
    @ColumnInfo(name = "questionId") var questionId: Int = 0

)
