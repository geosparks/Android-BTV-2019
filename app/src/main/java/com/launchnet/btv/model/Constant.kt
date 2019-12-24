package com.launchnet.btv.model

import android.content.Context
import android.widget.Toast

object Constant {
    const val NETWORK_ERROR: String = "Network error try again"
    const val DB_NAME = "BTV"
    const val LOGIN = "login"
    const val USERID = "userid"
    const val EMAIL = "email"
    const val APP_STATE: String = "appState"
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}