package com.example.generatorfaktur.DBManager

import android.content.Context
import android.os.AsyncTask
import com.example.generatorfaktur.invoiceProperties.Entity

class BasicDBManager(private val context: Context) : DBManager {

    override fun addEntity(entity: Entity) {
        AsyncTask.execute {
            val database = AppDatabase.getDatabase(context)
            database.entityDao().insertEntity(entity)
        }
    }

    override fun deleteEntity(entity: Entity) {
        AsyncTask.execute {
            val database = AppDatabase.getDatabase(context)
            database.entityDao().deleteEntity(entity)
        }
    }

    override fun updateEntity(entity: Entity) {
        AsyncTask.execute {
            val database = AppDatabase.getDatabase(context)
            database.entityDao().updateEntity(entity)
        }
    }

    override fun getAllEntity(): List<Entity> {
        val database = AppDatabase.getDatabase(context)
        return database.entityDao().getAll()

    }
}