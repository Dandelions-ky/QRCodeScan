package com.dandelions.qrcodescan.Other

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.dandelions.qrcodescan.R

object ShareUtils {

    fun shareResult(context: Context, resultString: String) {
        when {
            resultString.indexOf("http") == 0
                    || resultString.contains("://") ->
                ShareUtils.getWebsite(resultString, context)
            resultString.indexOf("mailto:") == 0 ->
                ShareUtils.getMail(resultString, context)
            resultString.indexOf("tel:") == 0 ->
                ShareUtils.getTEL(resultString, context)
            resultString.indexOf("smsto:") == 0 ->
                ShareUtils.getSMS(resultString, context)
            else -> ShareUtils.shareText(resultString, context as Activity)
        }
    }

    fun getWebsite(resultString: String, context: Context) {
        val intent = Intent()
        try {
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(resultString)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "未安装该应用", Toast.LENGTH_SHORT).show()
        }

    }

    fun getMail(resultString: String, context: Context) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse(resultString)
        context.startActivity(intent)
    }

    fun getTEL(resultString: String, context: Context) {
        val uri = Uri.parse(resultString)
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = uri
        context.startActivity(intent)
    }

    fun getSMS(resultString: String, context: Context) {
        val strResult =
            resultString.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

        val uri = Uri.fromParts("sms", strResult[1], null)
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", strResult[2])
        context.startActivity(intent)
    }

    fun shareText(text: String, context: Activity) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.share)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val chooserIntent = Intent.createChooser(intent, context.title)
        context.startActivityForResult(chooserIntent, 1000)
    }

    /**
     * dip转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    fun dip2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f)
    }
}