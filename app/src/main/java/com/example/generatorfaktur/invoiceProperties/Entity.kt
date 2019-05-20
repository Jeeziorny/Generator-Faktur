package com.example.generatorfaktur.invoiceProperties

class Entity(
    val name: String,
    val adress: String,
    val postal: String,
    val nip: String
) {

    fun getKeywords() : ArrayList<String> {
        val result = ArrayList<String>()
        result.addAll(listOf(name, adress, postal, nip))
        return result
    }
}