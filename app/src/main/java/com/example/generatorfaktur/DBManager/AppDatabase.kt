package com.example.generatorfaktur.DBManager

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.example.generatorfaktur.invoiceProperties.Entity
import java.lang.Exception


@Database(entities = [(Entity::class)], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entityDao(): EntityDAO

    companion object {

        // Instance of AppDatabase
        var instance: AppDatabase? = null


        // Returns instance of database
        fun getDatabase(context: Context) : AppDatabase {

            // Fragment from Singleton pattern
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        try {

                            //Create an Instance
                            instance = Room.databaseBuilder(
                                context,
                                AppDatabase::class.java,
                                "database.db"
                            ).fallbackToDestructiveMigration()
                                .build()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            return instance!!
        }
    }
}