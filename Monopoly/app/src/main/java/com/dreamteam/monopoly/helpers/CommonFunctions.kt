package com.dreamteam.monopoly.helpers

import android.app.Activity
import android.view.View
import com.dreamteam.monopoly.R
import com.muddzdev.styleabletoastlibrary.StyleableToast
import com.tapadoo.alerter.Alerter

fun showToast(v: View, text: String) {
    StyleableToast.makeText(v.context, text, R.style.TinyToast).show()
}

fun makeAlert(activity: Activity, title: String, text: String) {
    Alerter.create(activity)
            .setTitle(title)
            .setText(text)
            .setBackgroundColorRes(R.color.colorAccent)
            .show()
}