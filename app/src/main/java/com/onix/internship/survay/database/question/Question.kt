package com.onix.internship.survay.database.question

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question (

    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "text") var text: String = "",
    @ColumnInfo(name = "type") var type: Int = 0,
    @ColumnInfo(name = "testId") var testId: Int = 0

)