package com.example.autospa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PaymentProcessingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_processing)

        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            val intent = Intent(this@PaymentProcessingActivity, PaymentSuccessActivity::class.java)
            startActivity(intent)
        }
    }
}