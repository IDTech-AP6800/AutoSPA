package com.example.autospa

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PaymentSuccessActivity: AppCompatActivity() {

    lateinit var sparkle_1: ImageView
    lateinit var sparkle_2: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        /*
        shine_1 = findViewById(R.id.shine_1)
        shine_2 = findViewById(R.id.shine_2)

        shineAnimation()

         */
        sparkle_1 = findViewById(R.id.sparkle_1)
        sparkle_2 = findViewById(R.id.sparkle_2)

        sparkleAnimation()

    }

    private fun sparkleAnimation() {

        val moveRightS1 = ObjectAnimator.ofFloat(sparkle_1, "translationX", 3f, -3f).apply {
            duration = 750
        }
        val moveLeftS1 = ObjectAnimator.ofFloat(sparkle_1, "translationX", -3f, 3f).apply {
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
}