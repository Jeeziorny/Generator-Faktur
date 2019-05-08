package com.example.generatorfaktur.DBManager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.generatorfaktur.invoiceProperties.Entity


class BasicDBManager(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), DBManager {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_ENTITY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ENTITY")
        onCreate(db)
    }

    override fun addEntity(entity: Entity) {
        val values = ContentValues()
        values.put(COLUMN_NAME, entity.name)
        values.put(COLUMN_ADRESS, entity.adress)
        values.put(COLUMN_POSTAL, entity.postal)
        values.put(COLUMN_NIP, entity.nip)
        values.put(COLUMN_PHONE, entity.phoneNumber)

        val db = this.writableDatabase
        db.insert(TABLE_ENTITY, null, values)
        db.close()
    }

    override fun getAllEntity(): ArrayList<Entity> {
        val list = ArrayList<Entity>()
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM $TABLE_ENTITY ORDER BY $COLUMN_NAME", null)
        if (c.moveToFirst()) {
            do {
                val entity = Entity()
                entity.name = c.getString(c.getColumnIndex(COLUMN_NAME))
                entity.adress = c.getString(c.getColumnIndex(COLUMN_ADRESS))
                entity.postal = c.getString(c.getColumnIndex(COLUMN_POSTAL))
                entity.nip = c.getString(c.getColumnIndex(COLUMN_NIP))
                entity.phoneNumber = c.getString(c.getColumnIndex(COLUMN_PHONE))
                list.add(entity)
            } while (c.moveToNext())
        }
        c.close()
        return list
    }

    override fun deleteEntity(entity: Entity) {
        val db = writableDatabase
        db.delete(TABLE_ENTITY, "id=?", arrayOf(entity.name))
        db.close()
    }

    override fun updateEntity(entity: Entity) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, entity.name)
        values.put(COLUMN_ADRESS, entity.adress)
        values.put(COLUMN_POSTAL, entity.postal)
        values.put(COLUMN_NIP, entity.nip)
        values.put(COLUMN_PHONE, entity.phoneNumber)

        db.update(TABLE_ENTITY, values, "id=?", arrayOf(entity.name))
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Database.db"

        // TABLE NAMES
        val TABLE_ENTITY = "Entity"

        // COLUMNS
        val COLUMN_NAME = "name"
        val COLUMN_ADRESS = "adress"
        val COLUMN_POSTAL = "postal"
        val COLUMN_NIP = "nip"
        val COLUMN_PHONE = "phone"


        val CREATE_ENTITY_TABLE = ("CREATE TABLE " +
                TABLE_ENTITY + "("
                + COLUMN_NAME + " TEXT PRIMARY KEY,"
                + COLUMN_ADRESS + " TEXT,"
                + COLUMN_POSTAL + " TEXT,"
                + COLUMN_NIP + " TEXT,"
                + COLUMN_PHONE + " TEXT )")

    }
}

