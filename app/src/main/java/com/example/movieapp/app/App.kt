package com.example.movieapp.app

import android.app.Application
import androidx.room.Room
import com.example.movieapp.room.DataBase
import com.example.movieapp.room.DatabaseDao
import java.lang.IllegalStateException

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: DataBase? = null
        private const val DB_NAME = "Films.db"
        fun getDatabaseDAO(): DatabaseDao {
            if (db == null) {
                initializeDb()
            }
            return db!!.databaseDao()
        }

        private fun initializeDb() {
            synchronized(DataBase::class.java) {
                if (db == null) {
                    if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        DataBase::class.java,
                        DB_NAME
                    ).build()
                }
            }
        }

    }
}