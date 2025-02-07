package com.example.autospa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.animation.ValueAnimator
import android.app.ActivityOptions
import android.widget.ImageView
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import android.widget.TextView
import kotlin.random.Random

class PaymentProcessingActivity : AppCompatActivity() {
    // Create a handler to schedule a task on the main (UI) thread
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_processing)

        // Delay for 5 seconds and then go to Payment Success
        handler.postDelayed({
            val intent = Intent(this, PaymentSuccessActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
            startActivity(intent, options.toBundle())
            finish()
        }, 5000)


        // Find text and apply float animation
        val textTitle: TextView = findViewById(R.id.text_title)
        animateFloatingText(textTitle, 10f, 800L) // Moves up/down by 10px every .8s

        // Find car image and apply float & tilt animation
        val car: ImageView = findViewById(R.id.car)
        animateFloatingImage(car, 1000L, 15f, Random.nextBoolean())
        animateTilt(car, 45f, 5f, 700L)

        // Find each bubble image and store in a variable
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

        // Apply float animation to each bubble and randomize which bubbles start moving up or down
        animateFloatingImage(smallBubble1, 1000L, 15f, Random.nextBoolean())
        animateFloatingImage(largeBubble1, 1700L, 15f, Random.nextBoolean())
        animateFloatingImage(smallBubble2, 1200L, 15f, Random.nextBoolean())
        animateFloatingImage(largeBubble2, 2000L, 15f, Random.nextBoolean())
        animateFloatingImage(smallBubble3, 1100L, 15f, Random.nextBoolean())
        animateFloatingImage(largeBubble3, 1900L, 15f, Random.nextBoolean())
        animateFloatingImage(smallBubble4, 1300L, 15f, Random.nextBoolean())
        animateFloatingImage(largeBubble4, 1600L, 15f, Random.nextBoolean())
        animateFloatingImage(smallBubble5, 900L, 15f, Random.nextBoolean())
        animateFloatingImage(largeBubble5, 1500L, 15f, Random.nextBoolean())
    }

    // Animates text to float up and down continuously
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

    // Animates an image to float up and down continuously
    private fun animateFloatingImage(img: ImageView, duration: Long, floatHeight: Float, startMovingUp: Boolean) {
        val start = if (startMovingUp) 0f else -floatHeight
        val end = if (startMovingUp) -floatHeight else 0f

        ValueAnimator.ofFloat(start, end).apply {
            this.duration = duration
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE

            addUpdateListener { animation ->
                img.translationY = animation.animatedValue as Float
            }

            start()
        }
    }

    // Animates an image to tilt back and forth continuously
    private fun animateTilt(img: ImageView, initialAngle: Float, tiltRange: Float, duration: Long) {
        ValueAnimator.ofFloat(initialAngle - tiltRange, initialAngle + tiltRange).apply {
            this.duration = duration
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { animation ->
                img.rotation = animation.animatedValue as Float
            }
            start()
        }
    }

}
