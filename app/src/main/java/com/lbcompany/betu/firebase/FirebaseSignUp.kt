package com.lbcompany.betu.firebase

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.lbcompany.betu.model.User

/**
 * Base class for sign up a user with firebase authentication
 * You can sign up via Facebook, Twitter, Email and password and Google
 *
 *
 * You may provide the Context in order to save the user in SharedPreferences
 */
class FirebaseSignUp
/**
 * Default constructor
 * Set instance of FirebaseSharedPreferences object.
 * Set authentication listener
 * Set Database reference
 *
 * @param context needed to instantiate Shared Preferences
 */
(private val mContext: Context, private val mDatabase: DatabaseReference) {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val mCallback: SignupCallback

    private lateinit var userId: String

    init {
        mAuth.addAuthStateListener(setAuthListener())
        mCallback = mContext as SignupCallback
    }


    /**
     * Sets the authentication listener.
     * When user is signed in it saves in Shared Preferences the user E-mail and Status.
     *
     * @return authentication listener
     */
    private fun setAuthListener(): FirebaseAuth.AuthStateListener {
        return object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                val user = firebaseAuth.currentUser
                if (user != null) {
                    if (user.email != null) {
                        // User is signed in
                        userId = user.uid
                        User.userID = user.uid
                    }
                }
            }
        }
    }

    /**
     * Sign up with E-mail and password authentication
     *
     *
     * Saves in Shared Preferences the user chats, id, username, and image url if the user
     * is correctly signed in.
     *
     * @param password String to set the user entered password
     */
    fun CreateAuthUserWithEmailAndPassword(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mContext as Activity) { task ->
                    if (task.isSuccessful) {
                        val map = HashMap<String, Any>()
                        map.put("username", User.username!!)
                        map.put("money", 30.50F)
                        mDatabase.child("users").child(User.userID).setValue(map)
                        mCallback.onSignUpSucceed()
                    } else {
                        mCallback.onSignUpFailed()
                    }
                }
    }
}
