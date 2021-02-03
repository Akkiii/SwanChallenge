package com.example.swantest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.swantest.listeners.PullRequestDao
import com.example.swantest.model.PullRequestModel
import com.example.swantest.model.UserModel

@Database(
    entities = arrayOf(PullRequestModel::class, UserModel::class),
    version = 1,
    exportSchema = false
)
abstract class PullRequestDatabase : RoomDatabase() {

    abstract fun pullRequestDao(): PullRequestDao

    companion object {
        @Volatile
        private var INSTANCE: PullRequestDatabase? = null

        fun getDatabase(context: Context): PullRequestDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PullRequestDatabase::class.java,
                    "pullrequest_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}