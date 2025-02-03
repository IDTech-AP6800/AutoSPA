package com.example.autospa

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.autospa.activities.NavigationBar

class EmailReceiptActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_receipt)
        NavigationBar(this)

        //Listener to lead to Receipt Sent
        val continueButton = findViewById<Button>(R.id.email_receipt_continue)
        continueButton.setOnClickListener {
            val intent = Intent(this, ReceiptSentActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this, R.anim.fade_in, R.anim.fade_out
            )
            startActivity(intent, options.toBundle())
        }

    }
}