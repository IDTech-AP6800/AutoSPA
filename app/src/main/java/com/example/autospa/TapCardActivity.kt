package com.example.autospa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.idtech.zsdk_client.Client
import com.idtech.zsdk_client.GetDevicesAsync
import com.idtech.zsdk_client.SetAutoAuthenticateAsync
import com.idtech.zsdk_client.SetAutoCompleteAsync
import com.idtech.zsdk_client.StartTransactionAsync
import com.idtech.zsdk_client.StartTransactionResponseData


class TapCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap_card)
    }
}