package com.example.generatorfaktur

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.example.generatorfaktur.DBManager.SellerData
import kotlinx.android.synthetic.main.prefs_activity.*

class PrefsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prefs_activity)
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        )
        setTexts()
    }

    fun confirmOnClick(view: View) {
        if (isCorrect()) {
            saveData()
            finish()
        }
    }

    private fun isCorrect(): Boolean {
        if (dealerNameText.text.toString().isBlank()) {
            showInformDialog("Wprowadzona nazwa jest niepoprawna !")
            return false
        }
        if (!Validator.checkNip(dealerNIPText.text.toString())) {
            showInformDialog("Wprowadzony nip jest niepoprawny !")
            return false
        }
        if (dealerAdressText.text.toString().isBlank()) {
            showInformDialog("Wprowadzony adres jest niepoprawny !")
            return false
        }
        if (!Validator.checkPostal(dealerPostalText.text.toString())) {
            showInformDialog("Wprowadzony kod pocztowy jest niepoprawny !")
            return false
        }
        if (dealerCityText.text.toString().isBlank()) {
            showInformDialog("Wprowadzona nazwa miasta jest niepoprawna !")
            return false
        }
        if (dealerBankNameText.text.toString().isBlank()) {
            showInformDialog("Wprowadzona nazwa banku jest niepoprawna !")
            return false
        }
        if (!Validator.checkAccNumber(dealerBankNumberText.text.toString())) {
            showInformDialog("Wprowadzony numer konta bankowego jest niepoprawny !")
            return false
        }
        if (!Validator.isNumeric(dealerPhoneText.text.toString())) {
            showInformDialog("Wprowadzony numer telefonu jest niepoprawny !")
            return false
        }
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

    private fun showInformDialog(message : String){
        val alert = AlertDialog.Builder(this)
        alert.setMessage(message)
        alert.setCancelable(true)
        alert.show()
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