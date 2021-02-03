package com.example.swantest.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.swantest.database.PullRequestDatabase

class SwanTestApp : Application() {
    companion object {
        lateinit var instance: SwanTestApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}