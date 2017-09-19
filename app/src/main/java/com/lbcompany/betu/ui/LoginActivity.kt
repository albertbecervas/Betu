package com.lbcompany.betu.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.lbcompany.betu.utils.AppSharedPreferences
import com.lbcompany.betu.R
import com.lbcompany.betu.firebase.FirebaseLogin
import com.lbcompany.betu.firebase.LoginCallback
import com.lbcompany.betu.model.User

import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), LoginCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mDatabase = FirebaseDatabase.getInstance().reference

        sign_in.setOnClickListener {
            FirebaseLogin(this).signInAuthUserWithEmailAndPassword(name.text.toString() + "@lbcompany.com"
                    , password.text.toString(), mDatabase)
        }

        sign_up.setOnClickListener { startActivity(Intent(this@LoginActivity, SignUpActivity::class.java)) }
    }

    override fun onLoginSucced() {
        toast("Log in succeed")
        if (checkBox.isChecked) {
            val mPrefs = AppSharedPreferences(this)
            mPrefs.setUser(User.username!!,User.name,User.userID!!)
        }
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

    override fun onLoginFailed(error: String?) {
        toast("Log in failed!")
    }
}
