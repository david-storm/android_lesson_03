package com.onix.internship.survay.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM users WHERE uid = :key LIMIT 1")
    suspend fun get(key: Int): User?

    @Query("SELECT * FROM users WHERE login = :login AND password = :password LIMIT 1")
    suspend fun get(login: String, password: String): List<User>

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    suspend fun get(login: String): List<User>

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}