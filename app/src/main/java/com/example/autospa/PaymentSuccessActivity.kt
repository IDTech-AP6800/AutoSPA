package com.example.autospa

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PaymentSuccessActivity: AppCompatActivity() {

    lateinit var sparkle_1: ImageView
    lateinit var sparkle_2: ImageView

    private lateinit var currentStep: View
    private lateinit var nextStep: View
    private lateinit var car: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        sparkle_1 = findViewById(R.id.sparkle_1)
        sparkle_2 = findViewById(R.id.sparkle_2)

        sparkleAnimation()

        currentStep = findViewById(R.id.success_current_step)
        nextStep = findViewById(R.id.success_next_step)
        car = findViewById(R.id.success_car)

        progressAnimation()

        // Delay for 3 seconds and then go to Receipt Options
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ReceiptOptionsActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }, 3000)

    }

    private fun sparkleAnimation() {

        val moveRightS1 = ObjectAnimator.ofFloat(sparkle_1, "translationX", -3f, 3f).apply {
            duration = 750
        }
        val moveLeftS1 = ObjectAnimator.ofFloat(sparkle_1, "translationX", 3f, -3f).apply {
            duration = 750
        }

        val moveRightS2 = ObjectAnimator.ofFloat(sparkle_2, "translationX", 3f, -3f).apply {
            duration = 750
        }
        val moveLeftS2 = ObjectAnimator.ofFloat(sparkle_2, "translationX", -3f, 3f).apply {
            duration = 750
        }

        val sparkleAnimation = AnimatorSet().apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    start()
                }
            })
        }

        val sparkle1Anim = AnimatorSet().apply {
            playSequentially(moveLeftS1, moveRightS1)
        }

        val sparkle2Anim = AnimatorSet().apply {
            playSequentially(moveLeftS2, moveRightS2)
        }

        sparkleAnimation.play(sparkle1Anim).with(sparkle2Anim)
        sparkleAnimation.start()

    }

    private fun progressAnimation() {
        // Set completed bubble to invisible at start
        currentStep.visibility = View.INVISIBLE

        car.post {
            val startPosition = car.x
            val carCenterOffset = car.width / 3f
            val endPosition = nextStep.x -carCenterOffset

            // Log.d("Debug", "Start: $startPosition, End: $endPosition")

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