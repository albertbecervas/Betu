package com.lbcompany.betu.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lbcompany.betu.R
import com.lbcompany.betu.model.User
import com.lbcompany.betu.utils.AppSharedPreferences
import kotlinx.android.synthetic.main.activity_bet_detail.*
import kotlinx.android.synthetic.main.item_options_layout.*

class BetDetailActivity : AppCompatActivity() {

    private var betId: String? = null

    private lateinit var mPrefs: AppSharedPreferences

    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bet_detail)

        mPrefs = AppSharedPreferences(this)
        val userId = mPrefs.getUserID()

        val map = intent.getSerializableExtra("map") as HashMap<String,String>

        mDatabase = FirebaseDatabase.getInstance().reference

        setViews(map, userId)
    }

    private fun setViews(map: HashMap<String, String>, userId: String) {
        bet_name.text = map.get("name")
        bet_description.text = map.get("description")
        first_option.text = map.get("firstOption")
        second_option.text = map.get("secondOption")
        betId = map.get("betId")

        first_option.setOnClickListener {
            val array = ArrayList<String>()
            array.add(betId.toString())
            mDatabase.child("users").child(userId).child("betsArray").setValue(array)
        }

        second_option.setOnClickListener {
            mDatabase.child("users").child(userId).child("betsArray").setValue("")
        }
    }
}
