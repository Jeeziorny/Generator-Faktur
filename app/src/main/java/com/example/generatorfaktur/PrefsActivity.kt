package com.example.generatorfaktur

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.generatorfaktur.DBManager.SellerData
import kotlinx.android.synthetic.main.prefs_activity.*

class PrefsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prefs_activity)
        setTexts()
    }


    fun confirmOnClick(view: View) {
        if (isCorrect()) {
            saveData()
            finish()
        }
    }

    private fun isCorrect(): Boolean {
        return true
    }

    private fun saveData() {
        val seller = SellerData(this)
        seller.setName(dealerNameText.text.toString())
        seller.setNip(dealerNIPText.text.toString())
        seller.setAddress(dealerAdressText.text.toString())
        seller.setPostal(dealerPostalText.text.toString())
        seller.setPhone(dealerPhoneText.text.toString())
        seller.setCity(dealerCityText.text.toString())
        seller.setBankName(dealerBankNameText.text.toString())
        seller.setBankNumber(dealerBankNumberText.text.toString())
        seller.setIsSellerSet(true)
    }

    private fun setTexts() {
        val seller = SellerData(this)
        if (seller.isSellerSet()) {
            dealerNameText.setText(seller.getName())
            dealerNIPText.setText(seller.getNip())
            dealerAdressText.setText(seller.getAddress())
            dealerPostalText.setText(seller.getPostal())
            dealerPhoneText.setText(seller.getPhone())
            dealerCityText.setText(seller.getCity())
            dealerBankNameText.setText(seller.getBankName())
            dealerBankNumberText.setText(seller.getBankNumber())
        }

    }
}