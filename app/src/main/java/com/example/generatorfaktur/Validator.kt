package com.example.generatorfaktur

class Validator {
    companion object {

        fun isNumeric(name: String): Boolean {
            return name.matches("[0-9]+".toRegex())
        }

        fun checkNip(n: String): Boolean {
            //Kontrola dlugosci
            if (n.length != 10)
                return false

            if (!isNumeric(n))
                return false

            //cyfra kontrolna
            val controlWeights = listOf(6, 5, 7, 2, 3, 4, 5, 6, 7)
            var controlSum = 0
            var iterator = 0
            var a = 0
            var b = 0
            for (i in 0..8) {
                a = n[i].toInt() - '0'.toInt()
                b = controlWeights[iterator]
                controlSum += a * b
                iterator++
            }
            if (controlSum % 11 != n[9].toInt() - '0'.toInt())
                return false

            return true
        }

        //sprawdza format ##-###
        fun checkPostal(p: String): Boolean {
            if (p.length != 6)
                return false

            for (i in 0..5) {
                if (i == 2) {
                    if (p[i] != '-') {
                        return false
                    }
                } else
                    if (!Character.isDigit(p[i]))
                        return false
            }
            return true
        }

        //nazwa krotsza niz 4 linijki na fakturze
        fun checkName(n: String): Boolean {
            if (n.isEmpty() || n.length > 27 * 4)
                return false
            return true
        }

        fun checkAccNumber(an: String): Boolean {
            if (an.length != 26)
                return false
            if (!isNumeric(an))
                return false
            return true
        }
    }
}