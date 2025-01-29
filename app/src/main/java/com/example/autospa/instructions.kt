package com.example.autospa

import android.animation.ObjectAnimator
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class instructions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions)
        val carImage: ImageView = findViewById(R.id.imageView2)

        // Get screen width dynamically
        val displayMetrics = DisplayMetrics()
        val screenWidth = displayMetrics.widthPixels.toFloat()

        fun animateCar() {
            carImage.post {
                carImage.translationX = -screenWidth
                val animator = ObjectAnimator.ofFloat(carImage, View.TRANSLATION_X, screenWidth).apply {
                    duration = 6000
                    startDelay = 100
                }
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        animateCar()
                    }
                })
                animator.start()
            }
        }
        animateCar()
    }
}
