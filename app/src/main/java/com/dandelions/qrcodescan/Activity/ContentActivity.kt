package com.dandelions.qrcodescan.Activity

import android.app.Activity
import android.os.Bundle
import com.dandelions.qrcodescan.Other.ShareUtils
import com.dandelions.qrcodescan.R
import kotlinx.android.synthetic.main.activiy_content.*

class ContentActivity : Activity() {
    private var textResult = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiy_content)
        initParams()
        initView()
    }

    private fun initParams() {
        textResult = intent.getStringExtra("APP_SCAN_RESULT")!!
    }

    private fun initView() {
        text_content.setText(textResult)

        result_share.setOnClickListener {
            ShareUtils.shareResult(this, textResult)
        }

        back_history.setOnClickListener {
            finish()
        }
    }


}