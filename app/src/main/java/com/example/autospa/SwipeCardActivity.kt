package com.example.autospa

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.camera.view.PreviewView
import com.example.autospa.activities.NavigationBar

class SwipeCardActivity : AppCompatActivity() {
    private lateinit var imageVerticalCar: ImageView
    private lateinit var imageVerticalLine: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_card)
        NavigationBar(this)

        imageVerticalCar = findViewById(R.id.img_vertical_car)
        imageVerticalLine = findViewById(R.id.img_vertical_line)
        startAnimations(imageVerticalCar, imageVerticalLine)
    }
}

private fun startAnimations(car: ImageView, line: ImageView) {

    car.post {
        val startPos = car.y
        val endPos = (line.y + line.height) - car.height
        Log.d("Debug", "Start: $startPos, End: $endPos")


        // Car Swipe Indication Animations
        // 0  - 150
        val moveDown = ObjectAnimator.ofFloat(car, "y", endPos).apply {
            duration = 3000
        }

        val moveUp = ObjectAnimator.ofFloat(car, "y", startPos).apply {
            duration = 3000
        }

        val rotationTop = ObjectAnimator.ofFloat(car, "rotationX", 0f, 180f)
        val rotationBottom = ObjectAnimator.ofFloat(car, "rotationX", 180f, 0f)

        val swipeIndicationSet = AnimatorSet().apply {
            playSequentially(moveDown, rotationTop, moveUp, rotationBottom)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    start() // Restart the animation loop
                }
            })
            start()
        }
    }
}