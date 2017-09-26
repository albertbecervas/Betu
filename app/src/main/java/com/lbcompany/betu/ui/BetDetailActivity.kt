package com.lbcompany.betu.ui

import android.content.Intent
import android.os.Bundle
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lbcompany.betu.BaseActivity
import com.lbcompany.betu.R
import com.lbcompany.betu.utils.AppSharedPreferences
import kotlinx.android.synthetic.main.activity_bet_detail.*
import kotlinx.android.synthetic.main.item_options_layout.*
import org.jetbrains.anko.toast

class BetDetailActivity : BaseActivity() {
    private var betId: String? = null

    private lateinit var mPrefs: AppSharedPreferences

    private lateinit var mDatabase: DatabaseReference

    override fun getView(): Int {
        return R.layout.activity_bet_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPrefs = AppSharedPreferences(this)
        mDatabase = FirebaseDatabase.getInstance().reference

        val userId = mPrefs.getUserID()

        val bet = intent.getSerializableExtra("bet") as HashMap<*,*>

        setViews(bet, userId)
    }

    private fun setViews(bet: HashMap<*, *>, userId: String) {
        bet_name.text = bet["name"].toString()
        bet_description.text = bet["description"].toString()
        first_option.text = bet["option1"].toString()
        second_option.text = bet["option2"].toString()
        betId = bet["betId"].toString()

        first_option.setOnClickListener {
            val betObject = HashMap<String,String>()
            betObject.put(betId.toString(),betId.toString())
            betObject.put("option", "option1")
            mDatabase.child("users").child(mPrefs.getUserID()).child("bets").child(betId).setValue(betObject)

            mPrefs.setUserMoney(mPrefs.getUserMoney() - 5)
            mDatabase.child("users").child(userId).child("money").setValue(mPrefs.getUserMoney())
        }

        second_option.setOnClickListener {
            val betObject = HashMap<String,String>()
            betObject.put(betId.toString(),betId.toString())
            betObject.put("option", "option2")
            mDatabase.child("users").child(mPrefs.getUserID()).child("bets").child(betId).setValue(betObject)

            mPrefs.setUserMoney(mPrefs.getUserMoney() - 5)
            mDatabase.child("users").child(userId).child("money").setValue(mPrefs.getUserMoney())
        }

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, CloseBetActivity::class.java)
            intent.putExtra("betId", betId)
            startActivity(intent)
        }
    }
}
