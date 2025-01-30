package com.example.autospa

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.autospa.activities.NavigationBar

class InsertCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_card)
        NavigationBar(this)

        val car = findViewById<ImageView>(R.id.car)
        val bubble = findViewById<ImageView>(R.id.bubble_three)

        startCarAnimation(car, bubble)

        // Get the root view of the activity's layout
        val rootView: View = findViewById(android.R.id.content)

        // Set an OnClickListener to detect taps on the screen
        rootView.setOnClickListener {

            // Navigate to PaymentProcessingActivity
            val intent = Intent(this, PaymentProcessingActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.fade_in, // Enter animation
                R.anim.fade_out // Exit animation
            )
            startActivity(intent, options.toBundle())
        }
    }

    private fun startCarAnimation(car: ImageView, bubble: ImageView) {
        car.post {
            Log.d("Debug", "before car.x: ${car.x}, before car.y: ${car.y}")
            Log.d("Debug", "car.translationX: ${car.translationX}, car.translationY: ${car.translationY}")
            Log.d("Debug", "bubble_three.x: ${bubble.x}, bubble_three.y: ${bubble.y}")

            val bubbleCenterX = bubble.x + (bubble.width / 2)
            val bubbleCenterY = bubble.y + (bubble.height / 2)
            val bubbleRadius = bubble.width / 2

            // Ensure the car starts at the top of the bubble
            car.x = bubbleCenterX - (car.width / 2)
            car.y = bubbleCenterY - bubbleRadius - (car.height / 2)

            // Define a quarter-circle path (4th quadrant)
            val path = Path().apply {
                moveTo(car.x + (car.width / 2), car.y + (car.height / 2))
                arcTo(
                    bubbleCenterX - bubbleRadius,
                    bubbleCenterY - bubbleRadius,
                    bubbleCenterX + bubbleRadius,
                    bubbleCenterY + bubbleRadius,
                    270f,
                    -90f,
                    true
                )
            }

            // ObjectAnimator for following the path
            val pathAnimator = ObjectAnimator.ofFloat(car, View.X, View.Y, path).apply {
                duration = 2000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }

            // ObjectAnimator for rotation
            val rotationAnimator = ObjectAnimator.ofFloat(car, View.ROTATION, 0f, -90f).apply {
                duration = 2000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }

            // Play both animations together
            AnimatorSet().apply {
                playTogether(pathAnimator, rotationAnimator)
                start()
            }
        }
    }
}