package com.lbcompany.betu.ui

import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.lbcompany.betu.BaseActivity
import com.lbcompany.betu.R
import com.lbcompany.betu.utils.AppSharedPreferences
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {
    override fun getView(): Int {
        return R.layout.activity_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        deposit.setOnClickListener {
            val amounty = amount.text.toString()
            val mDatabase = FirebaseDatabase.getInstance().reference
            mDatabase.child("users").child(AppSharedPreferences(this@ProfileActivity).getUserID()).child("moneyToUpdate").setValue(amounty.toFloat())
        }
    }
}
