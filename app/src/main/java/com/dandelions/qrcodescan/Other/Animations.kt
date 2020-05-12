package com.dandelions.qrcodescan.Other

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.dandelions.qrcodescan.R

object Animations {

    fun showAnimation(view: View,context: Context) {
        view.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(context, R.anim.show_window);
        animation.fillAfter = true
        view.startAnimation(animation)
    }

    fun closeAnimation(view: View,context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.hide_window)
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                view.visibility = View.GONE
            }

            override fun onAnimationStart(p0: Animation?) {

            }

        })
        view.startAnimation(animation)
    }

    fun lineAnimation(view: View,context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.line_translate)
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                lineAnimation_D(view,context)
            }

            override fun onAnimationStart(p0: Animation?) {

            }

        })
        view.startAnimation(animation)
    }

    fun lineAnimation_D(view: View,context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.line_translated)
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                lineAnimation(view,context)
            }

            override fun onAnimationStart(p0: Animation?) {

            }

        })
        view.startAnimation(animation)
    }
}