package com.launchnet.btv.service

import android.content.Context
import android.location.Location
import android.os.AsyncTask
import com.geospark.lib.location.services.GeoSparkReceiver
import com.geospark.lib.model.GeoSparkError
import com.geospark.lib.model.GeoSparkUser
import com.launchnet.btv.db.getEmail
import com.launchnet.btv.db.getUserId
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject


class MyGeoSparkReceiver : GeoSparkReceiver() {

    override fun onLocationUpdated(
        context: Context,
        location: Location,
        geoSparkUser: GeoSparkUser,
        activity: String
    ) {
        UploadAsyncTask(context, location).execute()
    }

    override fun onError(p0: Context?, p1: GeoSparkError?) {
    }

    class UploadAsyncTask(private val context: Context, private val location: Location) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {
            try {
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("unique_id", context.getUserId())
                    jsonObject.put("full_name", context.getEmail())
                    jsonObject.put("latitude", location.latitude)
                    jsonObject.put("longitude", location.longitude)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                val client = OkHttpClient()
                val JSON = MediaType.parse("application/json; charset=utf-8")
                val body = RequestBody.create(JSON, jsonObject.toString())
                val request = Request.Builder()
                    .url("https://btv.launchnet.works/api/webhook")
                    .post(body)
                    .build()
                var response: Response? = null
                response = client.newCall(request).execute()
                val resStr = response!!.body()!!.string()
            } catch (e: Exception) {
            }
            return null
        }
    }

}