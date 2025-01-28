package com.example.autospa

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel

class WashPackageActivity: AppCompatActivity() {

    lateinit var carousel: Carousel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wash_package)

        val button = findViewById<Button>(R.id.button)
        val carousel = findViewById<Carousel>(R.id.carousel)
        button.setOnClickListener{
            val intent = Intent(this@WashPackageActivity, OptionalAddOnsActivity::class.java)
            startActivity(intent)
        }

        carousel.setAdapter(object: Carousel.Adapter {
            override fun count(): Int {
                TODO("Not yet implemented")
            }

            override fun populate(view: View?, index: Int) {
                TODO("Not yet implemented")
            }

            override fun onNewItem(index: Int) {
                TODO("Not yet implemented")
            }
        })


    }

}