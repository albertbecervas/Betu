package com.lbcompany.betu.ui

import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.lbcompany.betu.BaseActivity
import com.lbcompany.betu.R
import com.lbcompany.betu.model.User
import kotlinx.android.synthetic.main.activity_create_bet.*

class CreateBetActivity : BaseActivity() {
    override fun getView(): Int {
        return R.layout.activity_create_bet
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViews()
    }

    private fun setViews() {
        create_bet.setOnClickListener {
            setBet()
        }
    }

    private fun setBet() {
        val name = bet_name.text.toString()
        if (name.isEmpty()) {
            bet_name.error = "cannot be empty"
            return
        }

        val mDatabase = FirebaseDatabase.getInstance().reference
        val key = mDatabase.child("groupal_bets").push().key

        val option1Map = HashMap<String, Any>()
        option1Map.put("value", value1.text.toString())
        val option2Map = HashMap<String, Any>()
        option2Map.put("value", value2.text.toString())

        val map = HashMap<String, Any>()
        map.put("name", name)
        map.put("description", description.text.toString())
        map.put("option1", option1Map)
        map.put("option2", option2Map)
        map.put("cuote", 5)
        User.userID?.let { map.put("admin", it) }

        mDatabase.child("groupal_bets").child(key).setValue(map)
    }
}
