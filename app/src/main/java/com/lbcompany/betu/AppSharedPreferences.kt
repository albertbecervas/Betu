package com.lbcompany.betu

import android.content.Context
import android.content.SharedPreferences


class AppSharedPreferences(context: Context) {

    var mSharedPreferences: SharedPreferences = context.getSharedPreferences("BetUSharedPreferences", 0)

    fun setUsername(username: String){
        mSharedPreferences.edit()
                .putString("username", username)
                .apply()
    }

    fun getUsername(): String = mSharedPreferences.getString("username","null")

    fun setName(name: String?){
        mSharedPreferences.edit()
                .putString("name",name)
                .apply()
    }

    fun getName(): String = mSharedPreferences.getString("name","null")

    fun setUserID(userID: String){
        mSharedPreferences.edit()
                .putString("userID",userID)
                .apply()
    }

    fun getUserID(): String = mSharedPreferences.getString("userID", "null")

    fun setUser(username: String, name: String?, userID: String){
        mSharedPreferences.edit()
                .putString("username", username)
                .putString("name", name)
                .putString("userID", userID)
                .apply()
    }

    fun logout(){
        mSharedPreferences.edit().clear().apply()
    }
}