package com.example.autospa

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.autospa.activities.NavigationBar

class TapCardActivity : AppCompatActivity() {

    private var totalDue = CarWashSession.totalDue
    private lateinit var amountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap_card)
        NavigationBar(this)

        // Animate the car
        val car = findViewById<View>(R.id.car)
        val bubble3 = findViewById<View>(R.id.bubble_3)

        animateView(car)
        animateView(bubble3)

        // Navigate to PaymentProcessingActivity on tap
        val rootView: View = findViewById(android.R.id.content)
        rootView.setOnClickListener {
            val intent = Intent(this, PaymentProcessingActivity::class.java)
            startActivity(intent)
        }

        amountText = findViewById(R.id.totalDue)
        amountText.text = "Amount Due: $" + String.format("%.2f", totalDue)
    }

    private fun animateView(view: View) {
        // Move up and down
        val moveUp = ObjectAnimator.ofFloat(view, "translationY", -30f).setDuration(1000)
        val moveDown = ObjectAnimator.ofFloat(view, "translationY", 30f).setDuration(1000)
        moveUp.startDelay = 300
        moveDown.startDelay = 300

        moveUp.repeatMode = ObjectAnimator.REVERSE
        moveDown.repeatMode = ObjectAnimator.REVERSE
        moveUp.repeatCount = ObjectAnimator.INFINITE
        moveDown.repeatCount = ObjectAnimator.INFINITE


        // Rotate slightly
        val rotateLeft = ObjectAnimator.ofFloat(view, "rotation", -10f).setDuration(1000)
        val rotateRight = ObjectAnimator.ofFloat(view, "rotation", 10f).setDuration(1000)
        rotateLeft.repeatMode = ObjectAnimator.REVERSE
        rotateRight.repeatMode = ObjectAnimator.REVERSE
        rotateLeft.repeatCount = ObjectAnimator.INFINITE
        rotateRight.repeatCount = ObjectAnimator.INFINITE

        // Combine animations
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(moveUp, moveDown, rotateLeft, rotateRight)
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.duration = 1000
        animatorSet.start()

        // Loop animation using AnimatorListenerAdapter
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animatorSet.start() // Restart the animation
            }
        })
    }
}
