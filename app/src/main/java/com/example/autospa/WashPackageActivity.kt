package com.example.autospa

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.core.motion.Motion
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.autospa.activities.NavigationBar

/* This code controls the wash package selection for the car wash
  */
class WashPackageActivity : AppCompatActivity() {


    private lateinit var button: Button
    private lateinit var motionLayout: MotionLayout
    private var currPackage = 18
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wash_package)
        NavigationBar(this)

        // Find views
        button = findViewById(R.id.button_continue)
        motionLayout = findViewById(R.id.motion_layout)


        // Setup button listener
        buttonListener()

        // Save current page
        savePageListener()

    }

    private fun buttonListener() {
        button.setOnClickListener {
            CarWashSession.totalDue = currPackage.toDouble()
            val intent = Intent(this@WashPackageActivity, OptionalAddOnsActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }
    }

    private fun savePageListener() {
        motionLayout.addTransitionListener(object: MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if(currentId == R.id.previous){
                    currPackage = 12
                    Log.d("WASH", "12")
                }
                else if(currentId == R.id.next){
                    currPackage = 25
                    Log.d("WASH", "25")
                }
                else if(currentId == R.id.next_plat){
                    currPackage = 32
                    Log.d("WASH", "32")
                }
                else{
                    currPackage = 18
                    Log.d("WASH", "18")}
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
    }

}
