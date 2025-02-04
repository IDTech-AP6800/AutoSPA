package com.example.autospa

import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.autospa.activities.NavigationBar

class OptionalAddOnsActivity : AppCompatActivity() {
    private lateinit var currentStep: View
    private lateinit var nextStep: View
    private lateinit var car: View
    private lateinit var continueButton: Button
    private lateinit var amountText: TextView

    // Add-On Prices
    private val addOnPrices = mapOf(
        R.id.checkbox_air_freshener to 2.00,
        R.id.checkbox_extra_vacuum_time to 5.00,
        R.id.checkbox_pet_hair_removal to 10.00,
        R.id.checkbox_mat_cleaning to 2.00
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_optional_add_ons)

        // Initialize Navigation Bar
        NavigationBar(this)

        amountText = findViewById(R.id.add_ons_amount_text)
        updateTotalDue()

        currentStep = findViewById(R.id.add_ons_current_step)
        nextStep = findViewById(R.id.add_ons_next_step)
        car = findViewById(R.id.add_ons_car)

        continueButton = findViewById(R.id.add_ons_continue)

        continueButton.setOnClickListener {
            val intent = Intent(this, PaymentOptionsActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }

        setUpCheckboxListeners()

        progressAnimation()

    }

    private fun setUpCheckboxListeners() {
        for ((id, price) in addOnPrices) {
            val checkbox = findViewById<CheckBox>(id)
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    CarWashSession.totalDue += price
                } else {
                    CarWashSession.totalDue -= price
                }
                updateTotalDue()
            }
        }
    }

    private fun updateTotalDue() {
        amountText.text = "Amount Due: $" + String.format("%.2f", CarWashSession.totalDue)
    }

    private fun progressAnimation() {
        currentStep.visibility = View.INVISIBLE

        car.post {
            val startPosition = car.x
            val carCenterOffset = car.width / 3f
            val endPosition = nextStep.x - carCenterOffset

//            Log.d("Debug", "Start: $startPosition, End: $endPosition")

            val animator = ObjectAnimator.ofFloat(car, "x", endPosition).apply {
                duration = 1500
                startDelay = 500
            }

            animator.addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Float

                if (animatedValue > startPosition + 20 && currentStep.visibility == View.INVISIBLE) {
                    currentStep.visibility = View.VISIBLE
                }

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
