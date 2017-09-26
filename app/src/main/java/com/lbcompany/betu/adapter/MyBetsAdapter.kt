package com.lbcompany.betu.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lbcompany.betu.R
import com.lbcompany.betu.model.Bet
import com.lbcompany.betu.ui.BetDetailActivity

import kotlinx.android.synthetic.main.item_aux_layout.view.*

class MyBetsAdapter(private val context: Context, private val myBet: ArrayList<Bet>) : RecyclerView.Adapter<MyBetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_aux_layout, parent, false), context)

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = myBet[position]
        holder?.bind(item)
    }

    override fun getItemCount(): Int = myBet.size

    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Bet) {
            itemView.item_text.text = item.betName
            itemView.item_text.setOnClickListener {
                with(item) {
                    val intent = Intent(context, BetDetailActivity::class.java)
                    val map = HashMap<String, String>()
                    map.put("name", betName.toString())
                    map.put("description", description.toString())
                    map.put("option1", firstOption.toString())
                    map.put("option2", secondOption.toString())
                    map.put("betId", betId.toString())
                    intent.putExtra("bet", map)
                    context.startActivity(intent)
                }


            }
        }
    }
}