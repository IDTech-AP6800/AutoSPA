package com.example.autospa

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.autospa.activities.NavigationBar

class SMSReceiptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_receipt)

        NavigationBar(this)
        listenContinueButton()
    }

    private fun listenContinueButton() {
        val continueButton = findViewById<Button>(R.id.sms_receipt_continue)
        val phoneNumber = findViewById<EditText>(R.id.phone_number_text_box)
        val errorMessage = findViewById<TextView>(R.id.phone_error_message)
        val navigationBar = findViewById<View>(R.id.navigation_bar)

        continueButton.setOnClickListener {
            val phoneRegex = Regex("^(\\+\\d{1,3}[- ]?)?\\d{10}\$")

            if (!phoneRegex.matches(phoneNumber.text.toString().trim())) {
                // Display the error message
                errorMessage.text = "Phone number needs to be 10 digits\n\nTap to Return"
                errorMessage.setBackgroundColor(Color.parseColor("#CC000000"))

                // Make Continue button and navigation bar less visible
                continueButton.visibility = View.INVISIBLE
                navigationBar.visibility = View.INVISIBLE

                // Disable EditText, Continue, and NavBar button
                phoneNumber.isEnabled = false
                continueButton.isEnabled = false
                navigationBar.isEnabled = false

                listenErrorClick()
            } else {
                // Navigate to the Receipt Sent Page
                val intent = Intent(this, ReceiptSentActivity::class.java)
                val options = ActivityOptions.makeCustomAnimation(
                    this, R.anim.fade_in, R.anim.fade_out
                )
                startActivity(intent, options.toBundle())
            }
        }
    }

    // Creates a listener to reset the error message and restore visibility when the page is tapped
    private fun listenErrorClick() {
        val rootView = findViewById<View>(android.R.id.content)
        val errorMessage = findViewById<TextView>(R.id.phone_error_message)
        val continueButton = findViewById<Button>(R.id.sms_receipt_continue)
        val phoneNumber = findViewById<EditText>(R.id.phone_number_text_box)
        val navigationBar = findViewById<View>(R.id.navigation_bar)

        rootView.setOnClickListener {
            // Clear the error message and reset visuals
            errorMessage.text = ""
            errorMessage.setBackgroundColor(Color.parseColor("#00000000"))

            // Restore visibility of Continue button and navigation bar
            continueButton.visibility = View.VISIBLE
            navigationBar.visibility = View.VISIBLE

            // Re-enable EditText, Continue, and NavBar button
            phoneNumber.isEnabled = true
            continueButton.isEnabled = true
            navigationBar.isEnabled = true
        }
    }
}
