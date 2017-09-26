package com.lbcompany.betu.ui

import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lbcompany.betu.BaseActivity
import com.lbcompany.betu.R
import com.lbcompany.betu.utils.AppSharedPreferences
import kotlinx.android.synthetic.main.activity_close_bet.*
import kotlinx.android.synthetic.main.item_options_layout.*

class CloseBetActivity : BaseActivity() {

    private var winnerOption: String = ""
    private var winnerUsers = ArrayList<Any>()
    private var looserOption: String = ""
    private var looserUsers = ArrayList<Any>()

    private lateinit var mPrefs: AppSharedPreferences

    override fun getView(): Int {
        return R.layout.activity_close_bet
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mDatabase = FirebaseDatabase.getInstance().reference

        mPrefs = AppSharedPreferences(this)

        val betId = intent.getStringExtra("betId")

        first_option.setOnClickListener {
            mDatabase.child("groupal_bets").child(betId).child("winner_option").setValue("option1")
            winnerOption = "option1"
            looserOption = "option2"
        }

        second_option.setOnClickListener {
            mDatabase.child("groupal_bets").child(betId).child("winner_option").setValue("option1")
            winnerOption = "option2"
            looserOption = "option1"
        }

        close_bet.setOnClickListener {
            mDatabase.child("groupal_bets").child(betId).child("open").setValue("false")

            mDatabase.child("groupal_bets").child(betId).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    winnerUsers = dataSnapshot?.child(winnerOption)?.child("participants")?.value as ArrayList<Any>
                    looserUsers = dataSnapshot.child(looserOption)?.child("participants")?.value as ArrayList<Any>
                    val cuote = dataSnapshot.child("cuote").value as Long

                    val winnerNumber = winnerUsers.size
                    val looserNumber = looserUsers.size

                    val money = looserNumber * cuote
                    val winnerMoney = (money / winnerNumber )+ cuote

                    for (winnerId in winnerUsers)
                        mDatabase.child("users").child(winnerId.toString()).child("moneyToUpdate").setValue(winnerMoney)
                }
            })
        }
    }
}
