package com.example.autospa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.autospa.activities.NavigationBar

class TapCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap_card)
        NavigationBar(this)

        // Get the root view of the activity's layout
        val rootView: View = findViewById(android.R.id.content)

        // Set an OnClickListener to detect taps on the screen
        rootView.setOnClickListener {
            // Navigate to PaymentProcessingActivity
            val intent = Intent(this, PaymentProcessingActivity::class.java)
            startActivity(intent)
        }
    }
}