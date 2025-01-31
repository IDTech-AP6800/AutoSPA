package com.example.autospa

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ReceiptOptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt_options)

        //QR receipt option event listener
        val qrOption = findViewById<LinearLayout>(R.id.qr_code_receipt)
        qrOption.setOnClickListener {
            val intent = Intent(this, QRCodeReceiptActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this, R.anim.fade_in, R.anim.fade_out
            )
            startActivity(intent, options.toBundle())
        }

        //Email receipt option event listener
        val emailOption = findViewById<LinearLayout>(R.id.email_receipt)
        emailOption.setOnClickListener {
            val intent = Intent(this, EmailReceiptActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this, R.anim.fade_in, R.anim.fade_out
            )
            startActivity(intent, options.toBundle())
        }

        //Phone number receipt option event listener
        val smsOption = findViewById<LinearLayout>(R.id.sms_receipt)
        smsOption.setOnClickListener {
            val intent = Intent(this, SMSReceiptActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this, R.anim.fade_in, R.anim.fade_out
            )
            startActivity(intent, options.toBundle())
        }

        //No receipt option event listener
        val noReceiptOption = findViewById<LinearLayout>(R.id.no_receipt)
        noReceiptOption.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this, R.anim.fade_in, R.anim.fade_out
            )
            startActivity(intent, options.toBundle())
            finish()
        }
    }
}
