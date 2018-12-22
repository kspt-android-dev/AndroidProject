package com.dreamteam.monopoly.helpers

import android.app.Activity
import com.dreamteam.monopoly.R
import com.tapadoo.alerter.Alerter

fun makeTinyAlert(activity: Activity, text: String) {
    Alerter.create(activity)
            .setText(text)
            .setBackgroundColorRes(R.color.colorAccent)
            .setEnterAnimation(R.anim.alerter_slide_in_from_top)
            .setExitAnimation(R.anim.alerter_slide_out_to_top)
            .enableSwipeToDismiss()
            .setDuration(1500)
            .show()
}