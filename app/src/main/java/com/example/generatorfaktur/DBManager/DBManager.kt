package com.example.generatorfaktur.DBManager

import com.example.generatorfaktur.invoiceProperties.Entity

interface DBManager {
    fun addEntity(entity: Entity)
    fun deleteEntity(entity: Entity)
    fun updateEntity(entity: Entity)
    fun getAllEntity(): List<Entity>

    //TODO: Wypelnia swietlon xD
}