package com.example.autospa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.autospa.activities.NavigationBar

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        NavigationBar(this)
    }
}