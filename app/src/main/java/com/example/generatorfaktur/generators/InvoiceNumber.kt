package com.example.generatorfaktur.generators

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.*

internal class InvoiceNumber(context: Context) {
    private val fv = "FV/M/"
    private var sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)


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

        var month = m.toString()
        if(month.length==1){
            month="0"+month
        }
        return "$fv$y/$month/$nShared"
    }
}
