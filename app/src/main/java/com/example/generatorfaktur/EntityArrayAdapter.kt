package com.example.generatorfaktur

import android.content.Context
import android.content.Entity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class EntityArrayAdapter(context : Context, var data : ArrayList<Entity>) :
    ArrayAdapter<Entity>(context, R.layout.entity, data){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)

        //TODO : ----- Krzychu ----- Tu wrzucanie do listview klientów z bazy danych
    }
}