package com.example.generatorfaktur.DBManager

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.example.generatorfaktur.invoiceProperties.Entity
import java.lang.Exception


@Database(entities = [(Entity::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entityDao(): EntityDAO

    companion object {
        var instance: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        try {
                            instance = Room.databaseBuilder(
                                context,
                                AppDatabase::class.java,
                                "database.db"
                            ).build()
                        } catch (e: Exception) {
                            Log.i("invoice", e.message)
                        }
                    }
                }
            }
            return instance!!
        }
    }
}