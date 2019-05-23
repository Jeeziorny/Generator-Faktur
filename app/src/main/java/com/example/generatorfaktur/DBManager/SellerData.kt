package com.example.generatorfaktur.DBManager

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.generatorfaktur.invoiceProperties.Entity

internal class SellerData(context: Context) {
    private val NAME = "name"
    private val POSTAL = "postal"
    private val PHONE = "phone"
    private val NIP = "nip"
    private val ADDRESS = "address"
    private val CITY = "city"
    private val BANK_NAME = "bankName"
    private val BANK_NUMBER = "bankNuumber"
    private val IS_SELLER_SET = "IsSellerSet"

    private val DEFAULT = "DEFAULT_VALUE"
    private var sharedPref: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    public fun isSellerSet(): Boolean{
        return sharedPref.getBoolean(IS_SELLER_SET, false)
    }

    public fun setIsSellerSet(isSet : Boolean){
        val edit = sharedPref.edit()
        edit.putBoolean(IS_SELLER_SET,isSet)
        edit.apply()
    }

    public fun setSeller(entity : Entity){
        setPostal(entity.postal)
        setPhone(entity.phoneNumber)
        setNip(entity.nip)
        setName(entity.name)
        setAddress(entity.address)
        setIsSellerSet(true)
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

    public fun get(tag: String): String {
        return sharedPref.getString(tag, DEFAULT)!!
    }

    private fun set(tag: String, s: String) {
        val edit = sharedPref.edit()
        edit.putString(tag, s)
        edit.apply()
    }

}
