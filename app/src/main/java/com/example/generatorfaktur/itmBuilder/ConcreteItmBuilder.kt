package com.example.generatorfaktur.itmBuilder

import com.example.generatorfaktur.Invoice

class ConcreteItmBuilder(invoice: Invoice) : AbstractItmBuilder(invoice) {
    override fun setName(name: String): Invoice {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setUnit(unit: String): Invoice {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setVatValue(vv: Double): Invoice {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setGrossValue(gv: Double): Invoice {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setQuantity(q: Double): Invoice {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setVat(vat: Double): Invoice {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setValue(value: Double): Invoice {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun build(): Invoice {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}