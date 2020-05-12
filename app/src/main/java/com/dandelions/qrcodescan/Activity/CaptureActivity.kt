package com.dandelions.qrcodescan.Activity

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.dandelions.qrcodescan.Other.Animations.closeAnimation
import com.dandelions.qrcodescan.Other.Animations.lineAnimation
import com.dandelions.qrcodescan.Other.Animations.showAnimation
import com.dandelions.qrcodescan.Other.DaoUtils
import com.dandelions.qrcodescan.Other.ShareUtils
import com.dandelions.qrcodescan.R
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import kotlinx.android.synthetic.main.activity_capture.*
import org.litepal.LitePal

class CaptureActivity : Activity() {
    private var isTorch = false
    private var isScan = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(android.Manifest.permission.CAMERA),
                REQUEST_PERMISSION_CAMERA
            )
        }
        barcodeView.decodeContinuous(callback)
    }

    fun click(view: View) {
        when (view.id) {
            R.id.ivFlash -> flash()
            R.id.barcodeView -> hideResult()
            R.id.openHistory -> startActivity(Intent(this, HistoryActivity().javaClass))
            R.id.result_share -> ShareUtils.shareResult(this,text_result.text.toString())
            R.id.text_result -> toContent()
        }
    }

    private fun toContent() {
        val intent = Intent(this,ContentActivity().javaClass)
        intent.putExtra(APP_SCAN_RESULT,text_result.text.toString())
        startActivity(intent)
    }

    private fun hideResult() {
        if (scan_result.visibility != View.VISIBLE) {
            return
        }
        closeAnimation(scan_result, this)
        isScan = false
//        lineAnimation(scan_line, this)
    }

    private fun flash() {
        isTorch = !isTorch
        barcodeView.setTorch(isTorch)
    }

    override fun onResume() {
        barcodeView.resume()
        super.onResume()
//        lineAnimation(scan_line, this)
    }

    override fun onPause() {
        barcodeView.pause()
        super.onPause()
        if (scan_result.visibility == View.VISIBLE) {
            closeAnimation(scan_result, this)
        }
        isScan = false
    }

    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            if (result!!.text != null && !isScan) {
                isScan = true
//                scan_line.animation = null
//                scan_line.visibility = View.GONE
                text_result?.setText(result.text)
                showAnimation(scan_result, this@CaptureActivity)
                val vibrator = application.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(100)
                startActivity(intent)
                DaoUtils.saveResult(result.text)
            }
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == REQUEST_PERMISSION_CAMERA) {
            //未取到相机权限
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_title))
                    .setMessage(getString(R.string.dialog_message))
                    .setPositiveButton(getString(R.string.dialog_positive)) { _, _ ->
                        finish()
                    }.show()
            }
        }
    }


    companion object {
        private val REQUEST_PERMISSION_CAMERA = 1000
        private val APP_SCAN_RESULT = "APP_SCAN_RESULT"
    }


}