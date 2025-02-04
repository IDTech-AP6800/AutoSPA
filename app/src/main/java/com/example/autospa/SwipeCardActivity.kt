package com.example.autospa

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.camera.view.PreviewView
import com.example.autospa.activities.NavigationBar
import android.content.Intent
import com.idtech.zsdk_client.CancelTransactionAsync
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.GetDevicesAsync
import com.idtech.zsdk_client.SetAutoAuthenticateAsync
import com.idtech.zsdk_client.SetAutoCompleteAsync
import com.idtech.zsdk_client.StartTransactionAsync
import com.idtech.zsdk_client.StartTransactionResponseData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SwipeCardActivity : AppCompatActivity() {
    private lateinit var imageVerticalCar: ImageView
    private lateinit var imageVerticalLine: ImageView

    private var totalDue = CarWashSession.totalDue
    private lateinit var amountText: TextView
    private var devices: List<String> = emptyList()
    private var connectedDeviceId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_card)
        NavigationBar(this)

        imageVerticalCar = findViewById(R.id.img_vertical_car)
        imageVerticalLine = findViewById(R.id.img_vertical_line)
        startAnimations(imageVerticalCar, imageVerticalLine)

        amountText = findViewById(R.id.totalDue)
        amountText.text = "Amount Due: $" + String.format("%.2f", totalDue)
        // Start the swipe transaction process
        startSwipeCardTransaction()
    }

    private fun cancelTransaction() {
        connectedDeviceId?.let { connectedDeviceId ->
            CoroutineScope(Dispatchers.IO).launch {
                Client.CancelTransactionAsync(connectedDeviceId!!)
                Log.d(TAG, "Transaction canceled successfully.")
            }
        } ?: Log.d(TAG, "No connected device ID available for canceling transaction.")
    }

    private fun startSwipeCardTransaction() {
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
                val transInterfaceType: Int = 1 // 1 for MSR (Swipe)

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

        //When the activity is resumed, start the transaction again
        startSwipeCardTransaction()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTransaction()
    }

    companion object {
        private const val TAG = "SwipeCardActivity"
    }

}

private fun startAnimations(car: ImageView, line: ImageView) {

    car.post {
        val startPos = car.y
        val endPos = (line.y + line.height) - car.height
        Log.d("Debug", "Start: $startPos, End: $endPos")


        // Car Swipe Indication Animations
        // 0  - 150
        val moveDown = ObjectAnimator.ofFloat(car, "y", endPos).apply {
            duration = 3000
        }

        val moveUp = ObjectAnimator.ofFloat(car, "y", startPos).apply {
            duration = 3000
        }

        val rotationTop = ObjectAnimator.ofFloat(car, "rotationX", 0f, 180f)
        val rotationBottom = ObjectAnimator.ofFloat(car, "rotationX", 180f, 0f)

        val swipeIndicationSet = AnimatorSet().apply {
            playSequentially(moveDown, rotationTop, moveUp, rotationBottom)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    start() // Restart the animation loop
                }
            })
            start()
        }
    }
}