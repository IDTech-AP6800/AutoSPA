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
    private val navigateToMainActivity = Runnable {
        val intent = Intent(this@AppManualActivity, MainActivity::class.java)
        startActivity(intent)
        finish() // Close this activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_manual)

        NavigationBar(this)

        // Start the inactivity timer
        startInactivityTimer()
    }

    private fun startInactivityTimer() {
        handler.postDelayed(navigateToMainActivity, inactivityTimeout)
    }

    private fun resetInactivityTimer() {
        handler.removeCallbacks(navigateToMainActivity)
        startInactivityTimer()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        // Reset the inactivity timer on any user interaction
        resetInactivityTimer()
        return super.dispatchTouchEvent(ev)
    }

    override fun onPause() {
        super.onPause()
        // Cancel the timer when the activity goes to the background
        handler.removeCallbacks(navigateToMainActivity)
    }

    override fun onResume() {
        super.onResume()
        // Restart the inactivity timer
        startInactivityTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up the handler to prevent memory leaks
        handler.removeCallbacks(navigateToMainActivity)
    }
}
