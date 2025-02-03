package com.example.autospa

import android.animation.AnimatorSet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.widget.ImageView
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import android.widget.TextView
import kotlin.random.Random

class PaymentProcessingActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private var flipped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_processing)

        val rootView = findViewById<View>(android.R.id.content)
        rootView.setOnClickListener {
            val intent = Intent(this@PaymentProcessingActivity, PaymentSuccessActivity::class.java)
            startActivity(intent)
        }

        val smallBubble1: ImageView = findViewById(R.id.small_bubble_1)
        val largeBubble1: ImageView = findViewById(R.id.large_bubble_1)
        val smallBubble2: ImageView = findViewById(R.id.small_bubble_2)
        val largeBubble2: ImageView = findViewById(R.id.large_bubble_2)
        val smallBubble3: ImageView = findViewById(R.id.small_bubble_3)
        val largeBubble3: ImageView = findViewById(R.id.large_bubble_3)
        val smallBubble4: ImageView = findViewById(R.id.small_bubble_4)
        val largeBubble4: ImageView = findViewById(R.id.large_bubble_4)
        val smallBubble5: ImageView = findViewById(R.id.small_bubble_5)
        val largeBubble5: ImageView = findViewById(R.id.large_bubble_5)

        // Randomize which bubbles start moving up or down
//        animateFloatingBubble(smallBubble1, 3000, 15f, Random.nextBoolean())
//        animateFloatingBubble(largeBubble1, 3025, 15f, Random.nextBoolean())
//        animateFloatingBubble(smallBubble2, 2975, 15f, Random.nextBoolean())
//        animateFloatingBubble(largeBubble2, 3005, 15f, Random.nextBoolean())
//        animateFloatingBubble(smallBubble3, 3000, 15f, Random.nextBoolean())
//        animateFloatingBubble(largeBubble3, 3025, 15f, Random.nextBoolean())
//        animateFloatingBubble(smallBubble4, 2975, 15f, Random.nextBoolean())
//        animateFloatingBubble(largeBubble4, 3005, 15f, Random.nextBoolean())
//        animateFloatingBubble(smallBubble5, 3000, 15f, Random.nextBoolean())
//        animateFloatingBubble(largeBubble5, 3025, 15f, Random.nextBoolean())

        animateFloatingBubble(smallBubble1, 1000L, 15f, Random.nextBoolean()) // 1.5s
        animateFloatingBubble(largeBubble1, 1700L, 15f, Random.nextBoolean()) // 2.2s
        animateFloatingBubble(smallBubble2, 1200L, 15f, Random.nextBoolean()) // 1.7s
        animateFloatingBubble(largeBubble2, 2000L, 15f, Random.nextBoolean()) // 2.5s
        animateFloatingBubble(smallBubble3, 1100L, 15f, Random.nextBoolean()) // 1.6s
        animateFloatingBubble(largeBubble3, 1900L, 15f, Random.nextBoolean()) // 2.4s
        animateFloatingBubble(smallBubble4, 1300L, 15f, Random.nextBoolean()) // 1.8s
        animateFloatingBubble(largeBubble4, 1600L, 15f, Random.nextBoolean()) // 2.1s
        animateFloatingBubble(smallBubble5, 900L, 15f, Random.nextBoolean()) // 1.4s
        animateFloatingBubble(largeBubble5, 1500L, 15f, Random.nextBoolean()) // 2.3s

        val car: ImageView = findViewById(R.id.car) // Car image
        // Floating Car (Slower, Smoother)
        animateFloatingBubble(car, 1000L, 15f, Random.nextBoolean()) // Car moves more gently

        // Apply tilt motion
        animateTilt(car, 45f, 5f, 700L) // Tilts between 40° and 50° every 0.7s


        // Text animation
        val textTitle: TextView = findViewById(R.id.text_title)
        animateFloatingText(textTitle, 10f, 800L) // Moves up/down by 10px every 1.5s

    }

    private fun animateFloatingText(view: View, floatHeight: Float, duration: Long) {
        ValueAnimator.ofFloat(0f, -floatHeight).apply {
            this.duration = duration
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { animation ->
                view.translationY = animation.animatedValue as Float
            }
            start()
        }
    }

    private fun animateFloatingBubble(bubble: ImageView, duration: Long, floatHeight: Float, startMovingUp: Boolean) {
        val start = if (startMovingUp) 0f else -floatHeight
        val end = if (startMovingUp) -floatHeight else 0f

        ValueAnimator.ofFloat(start, end).apply {
            this.duration = duration
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE

            addUpdateListener { animation ->
                bubble.translationY = animation.animatedValue as Float
            }

            start()
        }
    }

    private fun animateTilt(bubble: ImageView, initialAngle: Float, tiltRange: Float, duration: Long) {
        ValueAnimator.ofFloat(initialAngle - tiltRange, initialAngle + tiltRange).apply {
            this.duration = duration
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { animation ->
                bubble.rotation = animation.animatedValue as Float
            }
            start()
        }
    }


}
