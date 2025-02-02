package com.example.autospa

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.autospa.activities.NavigationBar
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.GetDevicesAsync
import com.idtech.zsdk_client.SetAutoAuthenticateAsync
import com.idtech.zsdk_client.SetAutoCompleteAsync
import com.idtech.zsdk_client.StartTransactionAsync
import com.idtech.zsdk_client.StartTransactionResponseData
import kotlinx.coroutines.*
import android.util.Log
import com.idtech.zsdk_client.CancelTransactionAsync

class TapCardActivity : AppCompatActivity() {

    private var devices: List<String> = emptyList()
    private var connectedDeviceId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap_card)
        NavigationBar(this)

        // Animate the car
        val car = findViewById<View>(R.id.car)
        val bubble3 = findViewById<View>(R.id.bubble_3)

        animateView(car)
        animateView(bubble3)

        startTapTransaction()
    }

    private fun startTapTransaction() {
        CoroutineScope(Dispatchers.IO).launch {
            // Enumerate devices
            if (enumerateDevices()) {
                connectedDeviceId = devices.firstOrNull()
            }

            connectedDeviceId?.let { deviceId ->
                // Enable auto-authenticate
                Client.SetAutoAuthenticateAsync(deviceId, true)
                    .waitForCompletionWithTimeout(1000)

                // Enable auto-complete
                Client.SetAutoCompleteAsync(deviceId, true)
                    .waitForCompletionWithTimeout(1000)

                // Define transaction parameters
                val amount = 0.1
                val amountOther = 0.0
                val transType: UByte = 0u
                val transTimeout: UByte = 100u
                val transInterfaceType: Int = 2 // 2 for CTLS (Tap)

                // Start the transaction
                val startTransCmd = Client.StartTransactionAsync(
                    deviceId,
                    amount, amountOther, transType, transTimeout,
                    transInterfaceType.toUByte(), 3000
                )

                startTransCmd.waitForCompletion()

                // Check the transaction status
                val resultData: StartTransactionResponseData? = startTransCmd.getResultData()
                if (resultData != null) {
                    // If transaction succeeds, navigate to ProcessingActivity
                    withContext(Dispatchers.Main) {
                        navigateToProcessingActivity()
                    }
                }
            }
        }
    }

    private fun cancelTransaction() {
        connectedDeviceId?.let { connectedDeviceId ->
            CoroutineScope(Dispatchers.IO).launch {
                Client.CancelTransactionAsync(connectedDeviceId!!)
                Log.d(TAG, "Transaction canceled successfully.")
            }
        } ?: Log.d(TAG, "No connected device ID available for canceling transaction.")
    }

    private suspend fun enumerateDevices(): Boolean {
        return runCatching {
            val cmd = Client.GetDevicesAsync()
            cmd.waitForCompletionWithTimeout(3000)
            devices = cmd.devices
            devices.isNotEmpty()
        }.getOrDefault(false)
    }

    private fun navigateToProcessingActivity() {
        val intent = Intent(this, PaymentProcessingActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onPause() {
        super.onPause()

        //When the activity is paused, cancel transaction
        cancelTransaction()
    }

    override fun onResume() {
        super.onResume()

        //When the activity is resume, start the transaction again
        startTapTransaction()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTransaction()
    }

    companion object {
        private const val TAG = "TapCardActivity"
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
