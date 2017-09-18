package com.lbcompany.betu

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.lbcompany.betu.firebase.FirebaseSignUp
import com.lbcompany.betu.firebase.SignupCallback
import com.lbcompany.betu.model.User
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.toast

class SignUpActivity : AppCompatActivity(), SignupCallback {

    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val mDatabase = FirebaseDatabase.getInstance().reference

        sign_up.setOnClickListener {
            checkFields()

            dialog = progressDialog("Registering...")
            User.username = username.text.toString()
            User.name = name.text.toString()
            FirebaseSignUp(this,mDatabase).CreateAuthUserWithEmailAndPassword(name.text.toString()
                    + "@lbcompany.com",password.text.toString())
        }
    }

    private fun checkFields() {
        if (name.text.length < 3) {
            name.error = "Minimum 3 characters"
            return
        }
        if (username.text.length < 3) {
            username.error = "Minimum 3 characters"
            return
        }
        if (password.text.length < 3) {
            password.error = "Minimum 3 characters"
            return
        }
    }

    override fun onSignUpSucceed() {
        toast("Sign up succeed")
        if (dialog!!.isShowing) dialog?.dismiss()
        startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
    }

    override fun onSignUpFailed() {
        toast("Sign up failed")
        if (dialog!!.isShowing) dialog?.dismiss()

    }
}
