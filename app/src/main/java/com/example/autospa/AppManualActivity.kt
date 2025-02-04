package com.example.autospa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import com.example.autospa.activities.NavigationBar

class AppManualActivity : AppCompatActivity() {
    private val inactivityTimeout = 60000L // 60 seconds
    private val handler = Handler(Looper.getMainLooper())

    // Runnable that starts MainActivity and finishes the current activity after the timeout
    private val navigateToMainActivity = Runnable {
        val intent = Intent(this@AppManualActivity, MainActivity::class.java)
        startActivity(intent)
        finish() // Close this activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_manual)

        // Initialize the NavigationBar
        NavigationBar(this)

        // Start the inactivity timer
        startInactivityTimer()
    }

    // Starts the inactivity timer which triggers navigation after the timeout
    private fun startInactivityTimer() {
        handler.postDelayed(navigateToMainActivity, inactivityTimeout)
    }

    // Resets the inactivity timer by removing any existing callbacks and starting timer again
    private fun resetInactivityTimer() {
        handler.removeCallbacks(navigateToMainActivity)
        startInactivityTimer()
    }

    // If any touch event occurs, reset inactivity timer
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        // Reset the inactivity timer on any user interaction
        resetInactivityTimer()
        return super.dispatchTouchEvent(ev)
    }

    // Called when the activity is paused (when app goes to the background)
    override fun onPause() {
        super.onPause()
        // Cancel the timer when the activity goes to the background
        handler.removeCallbacks(navigateToMainActivity)
    }

    // Called when the activity is resumed (comes back to the foreground)
    override fun onResume() {
        super.onResume()
        // Restart the inactivity timer
        startInactivityTimer()
    }

    // Called when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        // Clean up the handler to prevent memory leaks
        handler.removeCallbacks(navigateToMainActivity)
    }
}
