package com.lbcompany.betu.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lbcompany.betu.BaseActivity
import com.lbcompany.betu.R
import com.lbcompany.betu.adapter.MyBetsAdapter
import com.lbcompany.betu.model.Bet
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    private lateinit var fbList: ArrayList<Bet>

    override fun getView(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViews()
    }

    override fun onResume() {
        super.onResume()
        setMyBetsAdapterList()
    }

    private fun setViews() {
        my_bets_layout.setOnClickListener {
            changeVisibility(my_bets_recycler)
        }

        create_bet.setOnClickListener { startActivity(Intent(this@MainActivity, CreateBetActivity::class.java)) }
    }

    private fun changeVisibility(itemView: View) {
        if (itemView.visibility == View.GONE) {
            itemView.visibility = View.VISIBLE
        } else {
            itemView.visibility = View.GONE
        }
    }

    private fun setMyBetsAdapterList() {
        fbList = ArrayList()
        val mDatabase = FirebaseDatabase.getInstance().reference

        mDatabase.child("groupal_bets").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                toast("error loading my bets")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                dataSnapshot?.children?.iterator()?.forEach { ds ->
                    val name = ds.child("name").value as String?
                    val description = ds.child("description").value as String?
                    val firstOption = ds.child("option1").child("value").value as String?
                    val secondOption = ds.child("option2").child("value").value as String?
                    val betId = ds.key
                    val bet = Bet(name, description, firstOption, secondOption, betId)
                    fbList.add(bet)
                }
                setMyBetsAdapter(fbList)
            }
        })
    }

    private fun setMyBetsAdapter(list: ArrayList<Bet>) {
        my_bets_recycler.visibility = View.VISIBLE
        val mAdapter = MyBetsAdapter(this@MainActivity, list)
        val layoutManager = LinearLayoutManager(this@MainActivity)
        my_bets_recycler.layoutManager = layoutManager
        my_bets_recycler.adapter = mAdapter
    }

}
