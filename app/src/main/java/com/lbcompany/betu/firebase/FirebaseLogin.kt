package com.lbcompany.betu.firebase

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.lbcompany.betu.model.User

/**
 * Base class for sign in a user with firebase authentication
 * You can sign in via Facebook, Twitter, Email and password and Google
 *
 *
 * You may provide the Context in order to save the user in SharedPreferences
 */
class FirebaseLogin
/**
 * Default constructor
 * Set instance of FirebaseSharedPreferences object.
 * Set authentication listener
 *
 * @param mContext needed to instantiate Shared Preferences
 */
(private val mContext: Context) {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val mCallback: LoginCallback = mContext as LoginCallback

    init {
        mAuth.addAuthStateListener(setAuthListener())
    }

    /**
     * Sign in with E-mail and password authentication
     *
     *
     * Saves in Shared Preferences the user chats, id, username, and image url if the user
     * is correctly signed in.
     *
     * @param email    String to set the user entered e-mail.
     * @param password String to set the user entered password
     */
    fun signInAuthUserWithEmailAndPassword(email: String, password: String, databaseReference: DatabaseReference) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mContext as Activity) { task ->
                    if (task.isSuccessful) {
                        val username = email.split("@")
                        databaseReference.child("users").child(User.userID).addValueEventListener(
                                object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError?) {
                                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                    }

                                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
                                        User.name = dataSnapshot?.child("name")?.value.toString()

                                        databaseReference.removeEventListener(this)
                                    }
                                }
                        )


                        User.username = username[0]
                        mCallback.onLoginSucced()
                    } else {
                        mCallback.onLoginFailed(task.exception?.message)
                    }
                }
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
                        User.userID = user.uid

                        mAuth.removeAuthStateListener(this)
                    }
                }
            }
        }
    }
}
