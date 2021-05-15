package com.onix.internship.survay.database.test

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class Test (
    @PrimaryKey(autoGenerate = true)  var id: Int = 0,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "description") var description: String = ""
)