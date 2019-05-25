package com.example.generatorfaktur.invoiceProperties

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import android.support.annotation.NonNull
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Parcelize
@Entity(tableName = "entity")
class Entity (
    @ColumnInfo(name = "name") var name: String,
    @NonNull
    @ColumnInfo(name = "address") var address: String,
    @NonNull
    @ColumnInfo(name = "postal") var postal: String,
    @NonNull
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String,
    @NonNull
    @ColumnInfo(name = "nip") var nip: String
) : Parcelable{
    constructor() : this("","","","","")

    /**
     * returns properties of object as ArrayList
     */
    fun getKeywords() : ArrayList<String> {
        val result = ArrayList<String>()
        result.addAll(listOf(name, address, postal, nip))
        return result
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id =0L
}