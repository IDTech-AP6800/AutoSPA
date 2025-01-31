package com.example.autospa

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout

class WashPackageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wash_package)

        val button = findViewById<Button>(R.id.button)
        var currPackage = 18

        button.setOnClickListener {
            CarWashSession.totalDue = currPackage.toDouble()
            val intent = Intent(this@WashPackageActivity, OptionalAddOnsActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }


        //Save Current Page
        // TODO: This is a very laggy work around, so we need to find a better way to implement
        var motionLayout = findViewById<MotionLayout>(R.id.motion_layout)
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
