package com.example.generatorfaktur

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.generatorfaktur.invoiceProperties.Entity
import java.util.*
import kotlin.collections.ArrayList


class EntityArrayAdapter(context: Context, var data: ArrayList<Entity>) :
    BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var displayData : ArrayList<Entity> = data.clone() as ArrayList<Entity>
    private var filterCharSequence = ""

    private class ViewHolder(row: View?) {
        var name: TextView? = null
        var postal: TextView? = null
        var adress: TextView? = null
        var nip: TextView? = null
        var phone: TextView? = null

        init {
            this.name = row?.findViewById(R.id.entityName)
            this.postal = row?.findViewById(R.id.entityPostal)
            this.adress = row?.findViewById(R.id.entityAddress)
            this.nip = row?.findViewById(R.id.entityNIP)
            this.phone = row?.findViewById(R.id.entityPhone)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.entity, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val item = displayData[position]
        viewHolder.name?.text = item.name
        viewHolder.phone?.text = item.phoneNumber
        viewHolder.adress?.text = item.address
        viewHolder.postal?.text = item.postal
        viewHolder.nip?.text = item.nip
        return view
    }

    override fun getItem(position: Int): Any {
        return displayData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return displayData.size
    }


    fun filter(charText: String) {
        filterCharSequence = charText.toLowerCase(Locale.getDefault())

        notifyDataSetChanged()
    }

    override fun notifyDataSetChanged() {
        displayData.clear()
        if (filterCharSequence.isEmpty()) {
            displayData.addAll(data)
        } else {
            for (entity in data) {
                if (entity.name.toLowerCase(Locale.getDefault()).contains(filterCharSequence)) {
                    displayData.add(entity)
                }
            }
        }
        super.notifyDataSetChanged()
    }



}