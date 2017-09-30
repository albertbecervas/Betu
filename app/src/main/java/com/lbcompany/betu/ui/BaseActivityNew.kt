package com.lbcompany.betu.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.*
import com.lbcompany.betu.utils.AppSharedPreferences
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import kotlinx.android.synthetic.main.toolbar_layout.*

abstract class BaseActivityNew: AppCompatActivity() {

    private val PICK_IMAGE = 1

    private lateinit var headerResult: AccountHeader
    private lateinit var profileDrawerItem: ProfileDrawerItem

    private lateinit var mPrefs: AppSharedPreferences
    private lateinit var mDatabase: DatabaseReference

    abstract fun getView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getView())

        mPrefs = AppSharedPreferences(this)
        mDatabase = FirebaseDatabase.getInstance().reference

        setUser()
        setToolbar()
        setDrawer()

        mDatabase.child("users").child(mPrefs.getUserID()).child("money").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dS: DataSnapshot?) {
                val doubleValue = dS?.value ?: return
                val float = java.lang.Float.valueOf(doubleValue.toString())
                mPrefs.setUserMoney(float)
                setUser()
            }
        })
    }

    private fun setUser() {
        if (mPrefs.getUsername() != "null") login.text = "${mPrefs.getUsername()} | ${mPrefs.getUserMoney()}€"
    }
}