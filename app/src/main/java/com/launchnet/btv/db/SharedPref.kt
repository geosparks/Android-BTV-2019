package com.launchnet.btv.db

import android.content.Context
import android.content.SharedPreferences
import com.launchnet.btv.model.Constant
import com.launchnet.btv.model.Constant.DB_NAME

private object SharedPref {

    private fun getInstance(context: Context): SharedPreferences {
        return context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE)
    }

    fun setString(context: Context, key: String, value: String) {
        val sharedPreferences = getInstance(context)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
        editor.commit()
    }

    fun getString(context: Context, key: String): String? {
        val sharedPreferences = getInstance(context)
        return sharedPreferences.getString(key, "")
    }

    fun setBoolean(context: Context, key: String, value: Boolean) {
        val sharedPreferences = getInstance(context)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
        editor.commit()
    }

    fun getBoolean(context: Context, key: String): Boolean? {
        val sharedPreferences = getInstance(context)
        return sharedPreferences.getBoolean(key, false)
    }
}

fun Context.setLoggedIn(value: Boolean) {
    SharedPref.setBoolean(this, Constant.LOGIN, value)
}

fun Context.isLoggedIn(): Boolean? {
    return SharedPref.getBoolean(this, Constant.LOGIN)
}

fun Context.setUserId(text: String) {
    SharedPref.setString(this, Constant.USERID, text)
}

fun Context.getUserId(): String? {
    return SharedPref.getString(this, Constant.USERID)
}

fun Context.setEmail(text: String) {
    SharedPref.setString(this, Constant.EMAIL, text)
}

fun Context.getEmail(): String? {
    return SharedPref.getString(this, Constant.EMAIL)
}

fun Context.setState(text: String) {
    SharedPref.setString(
        this,
        Constant.APP_STATE,
        text
    )
}

fun Context.getState(): String? {
    return SharedPref.getString(
        this,
        Constant.APP_STATE
    )
}