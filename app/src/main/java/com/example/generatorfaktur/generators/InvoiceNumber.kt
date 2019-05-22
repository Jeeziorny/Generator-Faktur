package com.example.generatorfaktur.generators

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.util.*

internal class InvoiceNumber(activity: Activity) {
    private val fv = "FV/M/"
    private var sharedPref: SharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)


    public fun generate(): String {
        val yShared = sharedPref.getInt("year", -1)
        val mShared = sharedPref.getInt("month", -1)
        var nShared = sharedPref.getLong("number", -1L)

        val calendar = Calendar.getInstance()
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)+1

        val editor = sharedPref.edit()
        if (yShared != y || mShared != m) {
            nShared = 0L
            editor.putInt("year", y)
            editor.putInt("month", m)
        }
        nShared++
        editor.putLong("number", nShared)
        editor.apply()

        return "$fv$y/$m/$nShared"
    }
}
