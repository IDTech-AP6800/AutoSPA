package com.example.autospa

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WashPackageActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_wash_package)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener{
            val intent = Intent(this@WashPackageActivity, OptionalAddOnsActivity::class.java)
            startActivity(intent)
        }


    }
}