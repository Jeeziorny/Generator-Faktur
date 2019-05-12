package com.example.generatorfaktur.DBManager

import android.content.Context
import android.os.AsyncTask
import com.example.generatorfaktur.invoiceProperties.Entity

class BasicDBManager(private val context: Context) : DBManager {
    init {
        init()
    }

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

    private fun init() {
        AsyncTask.execute {
            val database = AppDatabase.getDatabase(context)
            val list = database.entityDao().getAll()
            if (list.isEmpty()) {
                database.entityDao().insertEntity(
                    Entity(
                        "Wysypisko Sp. z o.o.",
                        "ul. Wyspiańskiego 10/1",
                        "00-001 Warszawa",
                        "+48 111 222 333",
                        "0123456789"
                    )
                )
                database.entityDao().insertEntity(
                    Entity(
                        "Wściekłe Krowy Sp.K.",
                        "ul. Nowowiejska 99",
                        "99-666 Bytom",
                        "+48 666 777 888",
                        "6910293847"
                    )
                )
                database.entityDao().insertEntity(
                    Entity(
                        "Rozlewnia BIMBEREK 93%",
                        "ul. Spirytusowa 40",
                        "40-500 Chmieleń",
                        "+48 098 321 567",
                        "1020304050"
                    )
                )
                database.entityDao().insertEntity(
                    Entity(
                        "Twoja Idealna Trumienka",
                        "ul. Cmentarna",
                        "11-777 Kosteczki",
                        "+48 936 741 154",
                        "0368521893"
                    )
                )
            }
        }
    }

}