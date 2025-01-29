package com.example.autospa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel

class WashPackageActivity : AppCompatActivity() {

    lateinit var carousel: Carousel
    private val washPackages = listOf(R.drawable.bronze_tier, R.drawable.silver_tier, R.drawable.gold_tier)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wash_package)

        val button = findViewById<Button>(R.id.button)
        carousel = findViewById(R.id.carousel) // Use class-level variable

        button.setOnClickListener {
            val intent = Intent(this@WashPackageActivity, OptionalAddOnsActivity::class.java)
            startActivity(intent)
        }

        // Properly setting up the carousel
        carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                return washPackages.size // Now returns the correct count
            }

            override fun populate(view: View, index: Int) {
                if (index < washPackages.size) {
                    view.setBackgroundResource(washPackages[index]) // Set the image dynamically
                }
            }

            override fun onNewItem(index: Int) {
                // Can be used to update UI on item switch
            }
        })
    }
}
