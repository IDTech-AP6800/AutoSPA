package com.example.autospa

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class PaymentOptionsActivity : AppCompatActivity() {
    private lateinit var currentStep: View
    private lateinit var nextStep: View
    private lateinit var car: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_options)

        currentStep = findViewById(R.id.options_current_step)
        nextStep = findViewById(R.id.options_next_step)
        car = findViewById(R.id.options_car)

        progressAnimation()
    }

    private fun progressAnimation() {
        // Set completed bubble to invisible at start
        currentStep.visibility = View.INVISIBLE

        car.post {
            val startPosition = car.x
            val carCenterOffset = car.width / 3f
            val endPosition = nextStep.x -carCenterOffset

            Log.d("Debug", "Start: $startPosition, End: $endPosition")

            val animator = ObjectAnimator.ofFloat(car, "x", endPosition).apply {
                duration = 1500
                startDelay = 500
            }

            animator.addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Float

                // When the car moves away from the completed bubble
                if (animatedValue > startPosition + 20 && currentStep.visibility == View.INVISIBLE) {
                    currentStep.visibility = View.VISIBLE
                }

                // When the car reaches the incomplete bubble
                if (animatedValue < endPosition - 50 && nextStep.alpha > 0f) {
                    val fadeOut = ObjectAnimator.ofFloat(nextStep, "alpha", 1f, 0f).apply {
                        duration = 500
                    }
                    fadeOut.start()
                }
            }

            animator.start()
        }
    }



}