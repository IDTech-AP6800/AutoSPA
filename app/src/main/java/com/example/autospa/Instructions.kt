package com.example.autospa

import android.animation.ObjectAnimator
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Instructions : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)

        val carImage: ImageView = findViewById(R.id.imageView2)

        // Get screen width dynamically
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels.toFloat()

        // Function to play the instruction audio when the page is opened
        fun playInstructionAudio() {
            mediaPlayer?.release() // Release any previous media player instance
            mediaPlayer = MediaPlayer.create(this, R.raw.instructions) // Play instructions.mp3
            mediaPlayer?.start()
        }

        // Function to animate the car
        fun animateCar() {
            carImage.post {
                carImage.translationX = -screenWidth

                val animator = ObjectAnimator.ofFloat(carImage, View.TRANSLATION_X, screenWidth).apply {
                    duration = 6000
                    startDelay = 100
                }

                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        animateCar() // Restart animation
                    }
                })

                animator.start()
            }
        }

        // Tap on page to return to Main Activity
        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Delay for 20 seconds and then go to Main Class
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 20000)

        // Play the audio when the page opens
        playInstructionAudio()
        animateCar()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Release MediaPlayer when the activity is destroyed
        mediaPlayer = null
    }
}
