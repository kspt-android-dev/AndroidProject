package com.dreamteam.monopoly.helpers

import android.app.Activity
import android.graphics.Color
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
            .enableSwipeToDismiss()
            .setDuration(10000)
            /*.addButton("Okay", R.style.AlertButton, View.OnClickListener {
                Toast.makeText(this@KotlinDemoActivity, "Okay Clicked", Toast.LENGTH_LONG).show()
            })
            .addButton("No", R.style.AlertButton, View.OnClickListener {
                Toast.makeText(this@KotlinDemoActivity, "No Clicked", Toast.LENGTH_LONG).show()*/
            .show()
}

fun makeTinyAlert(activity: Activity, title: String, text: String) {
    Alerter.create(activity)
            .setText(text)
            .setBackgroundColorRes(R.color.colorAccent)
            .enableSwipeToDismiss()
            .setDuration(5000)
            .show()
}