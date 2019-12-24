package com.launchnet.btv.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.geospark.lib.GeoSpark
import com.launchnet.btv.R
import com.launchnet.btv.db.getEmail
import com.launchnet.btv.db.setLoggedIn
import com.launchnet.btv.service.ImplicitService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onResume() {
        super.onResume()
        GeoSpark.startTracking(this)
        startService(Intent(this, ImplicitService::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setLoggedIn(true)
        if (!getEmail().isNullOrEmpty()) {
            txt_email.text = getEmail()
        }
        txt_home.setOnClickListener(this)
        txt_art.setOnClickListener(this)
        txt_event.setOnClickListener(this)
        txt_camp.setOnClickListener(this)
        txt_faq.setOnClickListener(this)
        txt_app.setOnClickListener(this)
        txt_contact.setOnClickListener(this)
        txt_ticket.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_home -> {
                openIntent(
                    "https://www.beyondthevalley.com.au/",
                    resources.getString(R.string.home)
                )
            }
            R.id.txt_art -> {
                openIntent(
                    "https://www.beyondthevalley.com.au/artists",
                    resources.getString(R.string.artists)
                )
            }
            R.id.txt_event -> {
                openIntent(
                    "https://www.beyondthevalley.com.au/event-info",
                    resources.getString(R.string.event_info)
                )
            }
            R.id.txt_camp -> {
                openIntent(
                    "https://www.beyondthevalley.com.au/camping",
                    resources.getString(R.string.camping)
                )
            }
            R.id.txt_faq -> {
                openIntent(
                    "https://www.beyondthevalley.com.au/faqs",
                    resources.getString(R.string.faqs)
                )
            }
            R.id.txt_app -> {
                openIntent(
                    "https://www.beyondthevalley.com.au/applications",
                    resources.getString(R.string.applications)
                )
            }
            R.id.txt_contact -> {
                openIntent(
                    "https://www.beyondthevalley.com.au/contact",
                    resources.getString(R.string.contact)
                )
            }
            R.id.txt_ticket -> {
                openIntent(
                    "https://www.eventbrite.com.au/e/beyond-the-valley-2019-tickets-66977193589?_ga=2.193163755.777748299.1576823468-621547275.1576823468",
                    resources.getString(R.string.tickets)
                )
            }
        }
    }

    private fun openIntent(url: String, name: String) {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra("", "")
        startActivity(
            Intent(this, WebActivity::class.java)
                .putExtra("URL", url)
                .putExtra("NAME", name)
        )
    }
}
