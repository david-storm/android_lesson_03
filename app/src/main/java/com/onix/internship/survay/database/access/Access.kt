package com.onix.internship.survay.database.access

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "access", primaryKeys = ["uid", "testId"])
data class Access(
    @ColumnInfo(name = "uid") var uid: Int = 0,
    @ColumnInfo(name = "testId") var testId: Int = 0,
)
