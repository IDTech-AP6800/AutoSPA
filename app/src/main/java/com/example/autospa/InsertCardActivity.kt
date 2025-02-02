package com.example.autospa

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.autospa.activities.NavigationBar
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.StartTransactionResponseData
import kotlinx.coroutines.*
import com.idtech.zsdk_client.*

class InsertCardActivity : AppCompatActivity() {

    private var devices: List<String> = emptyList()
    private var connectedDeviceId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_card)
        NavigationBar(this)

        val car = findViewById<ImageView>(R.id.car)
        val bubble = findViewById<ImageView>(R.id.bubble_three)

        startCarAnimation(car, bubble)

        // Get the root view of the activity's layout
        val rootView: View = findViewById(android.R.id.content)

        // Start the EMV transaction process
        startEMVTransaction()
    }

    private fun startEMVTransaction() {
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
                val transInterfaceType: Int = 4 // 4 for EMV (Insert)

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
        startEMVTransaction()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTransaction()
    }

    companion object {
        private const val TAG = "InsertCardActivity"
    }

    private fun startCarAnimation(car: ImageView, bubble: ImageView) {
        car.post {
            Log.d("Debug", "before car.x: ${car.x}, before car.y: ${car.y}")
            Log.d("Debug", "car.translationX: ${car.translationX}, car.translationY: ${car.translationY}")
            Log.d("Debug", "bubble_three.x: ${bubble.x}, bubble_three.y: ${bubble.y}")

            val bubbleCenterX = bubble.x + (bubble.width / 4)
            val bubbleCenterY = bubble.y + (bubble.height / 4)
            val bubbleRadius = bubble.width / 2

            // Ensure the car starts at the top of the bubble
            car.x = bubbleCenterX - (car.width / 2)
            car.y = bubbleCenterY - bubbleRadius - (car.height / 2)

            // Define a quarter-circle path (4th quadrant)
            val path = Path().apply {
                moveTo(car.x + (car.width / 2), car.y + (car.height / 2))
                arcTo(
                    bubbleCenterX - bubbleRadius,
                    bubbleCenterY - bubbleRadius,
                    bubbleCenterX + bubbleRadius,
                    bubbleCenterY + bubbleRadius,
                    270f,
                    -50f,
                    true
                )
            }

            // ObjectAnimator for following the path
            val pathAnimator = ObjectAnimator.ofFloat(car, View.X, View.Y, path).apply {
                duration = 2000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }

            // ObjectAnimator for rotation
            val rotationAnimator = ObjectAnimator.ofFloat(car, View.ROTATION, 0f, -40f).apply {
                duration = 2000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }

            // Play both animations together
            AnimatorSet().apply {
                playTogether(pathAnimator, rotationAnimator)
                start()
            }
        }
    }
}