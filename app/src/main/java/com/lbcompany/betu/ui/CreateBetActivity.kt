package com.lbcompany.betu.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.firebase.database.FirebaseDatabase
import com.lbcompany.betu.R
import kotlinx.android.synthetic.main.activity_create_bet.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CreateBetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_bet)

        setToolbar()

        setViews()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black)
        supportActionBar?.title = ""
        login.text = ""
    }

    private fun setViews() {
        create_bet.setOnClickListener {
            val name = bet_name.text.toString()
            if (name.isEmpty()) {
                bet_name.error = "cannot be empty"
                return@setOnClickListener
            }

            val mDatabase = FirebaseDatabase.getInstance().reference
            val key = mDatabase.child("bets").push().key

            val map = HashMap<String, Any>()
            map.put("bet_name", name)

            mDatabase.child("bets").child(key).setValue(map)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}
