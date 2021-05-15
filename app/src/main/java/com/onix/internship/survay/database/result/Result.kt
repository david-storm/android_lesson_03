package com.onix.internship.survay.database.result

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "results")
data class Result(

    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "date") var date: Long = Calendar.getInstance().timeInMillis,
    @ColumnInfo(name = "score") var score: Float = 0f,
    @ColumnInfo(name = "questions") var questions: Int = 0,
    @ColumnInfo(name = "uid") var uid: Int = 0,
    @ColumnInfo(name = "testId") var testId: Int = 0,

)
