package com.onix.internship.survay.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.onix.internship.survay.database.access.Access
import com.onix.internship.survay.database.access.AccessDatabaseDao
import com.onix.internship.survay.database.answer.Answer
import com.onix.internship.survay.database.answer.AnswerDatabaseDao
import com.onix.internship.survay.database.question.Question
import com.onix.internship.survay.database.question.QuestionDatabaseDao
import com.onix.internship.survay.database.result.Result
import com.onix.internship.survay.database.result.ResultDatabaseDao
import com.onix.internship.survay.database.test.Test
import com.onix.internship.survay.database.test.TestDatabaseDao
import com.onix.internship.survay.database.user.User
import com.onix.internship.survay.database.user.UserDatabaseDao

@Database(
    entities = [User::class, Access::class, Answer::class, Question::class, Result::class, Test::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDatabaseDao: UserDatabaseDao
    abstract val accessDatabaseDao: AccessDatabaseDao
    abstract val answerDatabaseDao: AnswerDatabaseDao
    abstract val questionDatabaseDao: QuestionDatabaseDao
    abstract val resultDatabaseDao: ResultDatabaseDao
    abstract val testDatabaseDao: TestDatabaseDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "user_database"
                    ).createFromAsset("test.db").build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}