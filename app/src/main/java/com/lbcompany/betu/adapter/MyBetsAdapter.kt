package com.lbcompany.betu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lbcompany.betu.R

import kotlinx.android.synthetic.main.item_aux_layout.*
import kotlinx.android.synthetic.main.item_aux_layout.view.*

class MyBetsAdapter(private val context: Context, private val myBetsList: ArrayList<Any>) : RecyclerView.Adapter<MyBetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_aux_layout, parent, false), context)

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = myBetsList[position]
        holder?.bind(item)
    }

    override fun getItemCount(): Int = myBetsList.size

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Any){
            itemView.item_text.text = item.toString()
        }
    }
}