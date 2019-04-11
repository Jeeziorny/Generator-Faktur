package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Summation (
    //dla kazdej wartosci vat na fakturze powinna byc oddzielna table Summation
    val totalNetto: Double,
    val totalVat: Double,
    val totalGros: Double
) : Parcelable