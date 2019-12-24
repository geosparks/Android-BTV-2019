package com.launchnet.btv.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.geospark.lib.GeoSpark
import com.geospark.lib.callback.GeoSparkCallBack
import com.geospark.lib.callback.GeoSparkEventsCallback
import com.geospark.lib.model.GeoSparkError
import com.geospark.lib.model.GeoSparkEvents
import com.geospark.lib.model.GeoSparkUser
import com.launchnet.btv.R
import com.launchnet.btv.db.setEmail
import com.launchnet.btv.db.setUserId
import com.launchnet.btv.model.Constant
import com.launchnet.btv.model.showToast
import com.launchnet.btv.service.ImplicitService
import com.launchnet.btv.util.NetworkHelper
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        txt_register.setOnClickListener {
            register()
        }
        GeoSpark.disableBatteryOptimization(this)
    }

    private fun show() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hide() {
        progress_bar.visibility = View.GONE
    }


    private fun register() {
        val email: String = edt_email.text.toString()
        if (email.isEmpty()) {
            edt_email.error = "Enter correct name or email"
        } else if (!NetworkHelper.isConnected(this)) {
            showToast(Constant.NETWORK_ERROR)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startTrackingQ(email)
            } else {
                startTracking(email)
            }
        }
    }

    private fun startTracking(email: String) {
        if (!GeoSpark.checkLocationPermission(this)) {
            GeoSpark.requestLocationPermission(this)
        } else if (!GeoSpark.checkLocationServices(this)) {
            GeoSpark.requestLocationServices(this)
        } else {
            createUser(email)
        }
    }

    private fun startTrackingQ(email: String) {
        if (!GeoSpark.checkActivityPermission(this)) {
            GeoSpark.requestActivityPermission(this)
        } else if (!GeoSpark.checkLocationPermission(this)) {
            GeoSpark.requestLocationPermission(this)
        } else if (!GeoSpark.checkBackgroundLocationPermission(this)) {
            GeoSpark.requestBackgroundLocationPermission(this)
        } else if (!GeoSpark.checkLocationServices(this)) {
            GeoSpark.requestLocationServices(this)
        } else {
            createUser(email)
        }
    }

    private fun createUser(email: String) {
        show()
        GeoSpark.createUser(this, email, object : GeoSparkCallBack {
            override fun onSuccess(geoSparkUser: GeoSparkUser) {
                GeoSpark.toggleEvents(
                    this@RegisterActivity,
                    true,
                    true,
                    true,
                    object : GeoSparkEventsCallback {
                        override fun onSuccess(p0: GeoSparkEvents?) {
                            startService(Intent(this@RegisterActivity, ImplicitService::class.java))
                            setUserId(geoSparkUser.userId)
                            setEmail(email)
                            hide()
                            GeoSpark.startTracking(this@RegisterActivity)
                            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                            finish()
                        }

                        override fun onFailure(p0: GeoSparkError?) {
                            hide()
                        }
                    })
            }

            override fun onFailure(goSparkError: GeoSparkError) {
                hide()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GeoSpark.REQUEST_CODE_ACTIVITY) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                register()
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                showToast("Please Grant Activity Permission")
            }
        } else if (requestCode == GeoSpark.REQUEST_CODE_LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                register()
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                showToast("Please Grant Location Permission")
            }
        } else if (requestCode == GeoSpark.REQUEST_CODE_BACKGROUND_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                register()
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                showToast("Please Grant Location Background Permission")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GeoSpark.REQUEST_CODE_LOCATION_ENABLED) {
            register()
        }
    }
}