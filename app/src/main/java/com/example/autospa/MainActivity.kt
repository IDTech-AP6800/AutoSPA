package com.example.autospa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var hasClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootView = findViewById<View>(android.R.id.content)
        val backgroundImageView = findViewById<ImageView>(R.id.background_3)

        // Load the translate animation
        val translateAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.translate_background)

        // Apply the animation to the background_1 ImageView
        backgroundImageView.startAnimation(translateAnimation)

        // Listen for the user's click event
        rootView.setOnClickListener {
            if (!hasClicked) {
                // Prevent the animation from playing multiple times
                hasClicked = true

                // Start the next activity when the user clicks
                val intent = Intent(this@MainActivity, WashPackageActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
