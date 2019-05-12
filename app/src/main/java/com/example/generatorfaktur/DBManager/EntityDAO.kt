package com.example.generatorfaktur.DBManager

import android.arch.persistence.room.*
import com.example.generatorfaktur.invoiceProperties.Entity

@Dao
interface EntityDAO {


        @Query("select * from entity order by name")
        fun getAll() : List<Entity>

        @Insert
        fun insertEntity(vararg student : Entity)

        @Delete
        fun deleteEntity(entity : Entity)

        @Update
        fun updateEntity(entity: Entity)
}
