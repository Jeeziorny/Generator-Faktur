package com.example.generatorfaktur

class Validator {
    companion object {

        /**
         * returns if String conzist of digits
         */
        fun isNumeric(name: String): Boolean {
            return name.matches("[0-9]+".toRegex())
        }

        fun checkNip(n: String): Boolean {
            //check if nip contains correct number of digits
            if (n.length != 10)
                return false

            if (!isNumeric(n))
                return false

            //for better understanding of validation algorithm check this link:
            //https://pl.wikipedia.org/wiki/NIP#Znaczenie_numeru
            val controlWeights = listOf(6, 5, 7, 2, 3, 4, 5, 6, 7)
            var controlSum = 0
            var iterator = 0
            var a: Int
            var b: Int
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

        /**
         * checks for format ##-###,
         * where # must be a digit.
         */
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

        fun checkProductName(n: String): Boolean {
            if (n.isEmpty() || n.length > 27 * 4)
                return false
            return true
        }

        //Checks account number
        fun checkAccNumber(an: String): Boolean {
            if (an.length != 26)
                return false
            if (!isNumeric(an))
                return false
            return true
        }
    }
}