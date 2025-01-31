package com.example.autospa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.autospa.activities.NavigationBar

class WashPackageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wash_package)

        NavigationBar(this)

        val button = findViewById<Button>(R.id.button)
        //var invisBubble = findViewById<View>(R.id.add_ons_current_step)
        //invisBubble
        var currPackage = 18

        button.setOnClickListener {
            CarWashSession.totalDue = currPackage.toDouble()
            val intent = Intent(this@WashPackageActivity, OptionalAddOnsActivity::class.java)
            startActivity(intent)
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
