package com.launchnet.btv.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.geospark.lib.GeoSpark
import com.launchnet.btv.R
import kotlinx.android.synthetic.main.activity_web_pages.*


class WebActivity : AppCompatActivity() {

    private var mWebView: WebView? = null
    private var url: String = ""
    private var name: String = ""

    override fun onResume() {
        super.onResume()
        GeoSpark.startTracking(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_pages)
        url = intent.getStringExtra("URL")
        name = intent.getStringExtra("NAME")
        mWebView = findViewById(R.id.web_view)
        img_back.setOnClickListener {
            onBackPressed()
        }
        img_refresh.setOnClickListener {
            loadUrl()
        }
        if (!name.isEmpty()) {
            txt_title.text = name
        }
        loadUrl()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadUrl() {
        mWebView?.requestFocus()
        mWebView?.settings!!.lightTouchEnabled = true
        mWebView?.settings!!.javaScriptEnabled = true
        mWebView?.loadUrl(url)
        mWebView?.webViewClient = Callback()
        mWebView?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress < 100) {
                    progress_bar.visibility = View.VISIBLE
                }
                if (progress >= 100) {
                    progress_bar.visibility = View.GONE
                }
            }
        }
    }

    private inner class Callback : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }

    }
}
