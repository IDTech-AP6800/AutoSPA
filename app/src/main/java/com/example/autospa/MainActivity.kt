package com.example.autospa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.autospa.viewmodels.MainViewModel
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.GetDevicesAsync

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var hasClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Client.GetDevicesAsync()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootView = findViewById<View>(android.R.id.content)
        val backgroundImageView = findViewById<ImageView>(R.id.background_3)

        // Load the translate animation
        val translateAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.translate_background)
        backgroundImageView.startAnimation(translateAnimation)

        // Listen for the user's click event
        rootView.setOnClickListener {
            if (!hasClicked) {
                hasClicked = true
                val intent = Intent(this@MainActivity, WashPackageActivity::class.java)
                startActivity(intent)
            }
        }

        // Observe connection status
        viewModel.connectionStatus.observe(this) { status ->
            Log.d("ConnectionStatus", "Connection status: $status")
        }

        // Trigger server connection
        viewModel.connectToServer("127.0.0.1", "42501", this)
    }
}
