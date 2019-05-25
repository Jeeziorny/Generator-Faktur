package com.example.generatorfaktur.DBManager

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.generatorfaktur.invoiceProperties.Entity

// Class which manage Seller's data
// using SharedPreferences.
internal class SellerData(context: Context) {

    // Keys :
    private val NAME = "name"
    private val POSTAL = "postal"
    private val PHONE = "phone"
    private val NIP = "nip"
    private val ADDRESS = "address"
    private val CITY = "city"
    private val BANK_NAME = "bankName"
    private val BANK_NUMBER = "bankNuumber"
    private val IS_SELLER_SET = "IsSellerSet"

    // Default returned value
    private val DEFAULT = "DEFAULT_VALUE"

    // Instace of SharedPreferences
    private var sharedPref: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    // Check if Seller's data were set
    public fun isSellerSet(): Boolean{
        return sharedPref.getBoolean(IS_SELLER_SET, false)
    }

    // Set true when seller data are correct.
    public fun setIsSellerSet(isSet : Boolean){
        val edit = sharedPref.edit()
        edit.putBoolean(IS_SELLER_SET,isSet)
        edit.apply()
    }


    public  fun getCity() :String{
        return get(CITY)
    }

    public  fun getBankName() :String{
        return get(BANK_NAME)
    }

    public  fun getBankNumber() :String{
        return get(BANK_NUMBER)
    }

    public fun getName(): String {
        return get(NAME)
    }

    public fun getNip(): String {
        return get(NIP)
    }

    public fun getPhone(): String {
        return get(PHONE)
    }

    public fun getPostal(): String {
        return get(POSTAL)
    }

    public fun getAddress(): String {
        return get(ADDRESS)
    }

    public fun setName(value: String) {
        set(NAME, value)
    }

    public fun setCity(value : String){
        set(CITY,value)
    }
    public fun setBankName(value : String){
        set(BANK_NAME,value)
    }
    public fun setBankNumber(value : String){
        set(BANK_NUMBER,value)
    }

    public fun setNip(value: String) {
        set(NIP, value)
    }

    public fun setPostal(value: String) {
        set(POSTAL, value)
    }

    public fun setPhone(value: String) {
        set(PHONE, value)
    }

    public fun setAddress(value: String) {
        set(ADDRESS, value)
    }

    // Getter form SharedPreferences
    public fun get(tag: String): String {
        return sharedPref.getString(tag, DEFAULT)!!
    }

    // Setter to SharedPreferences
    private fun set(tag: String, s: String) {
        val edit = sharedPref.edit()
        edit.putString(tag, s)
        edit.apply()
    }

}
