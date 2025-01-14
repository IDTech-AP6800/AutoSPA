package com.example.autospa

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import com.google.mlkit.vision.barcode.common.Barcode

class QRCodeViewModel(barcode: Barcode) {
    var boundingRect: Rect = barcode.boundingBox!!
    var qrCodeTouchCallback = { v: View, e: MotionEvent -> false }
}