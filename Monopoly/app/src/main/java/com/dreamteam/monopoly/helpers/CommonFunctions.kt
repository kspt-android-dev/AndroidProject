package com.dreamteam.monopoly.helpers

import android.app.Activity
import android.widget.Button
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dreamteam.monopoly.R
import com.dreamteam.monopoly.game.data.GameData.alertDuration
import com.dreamteam.monopoly.game.data.GameData.shakeEffectDuration
import com.dreamteam.monopoly.game.data.GameData.shakeRepeatCount
import com.tapadoo.alerter.Alerter

fun makeTinyAlert(activity: Activity, text: String) {
    Alerter.create(activity)
            .setText(text)
            .setBackgroundColorRes(R.color.colorAccent)
            .setEnterAnimation(R.anim.alerter_slide_in_from_top)
            .setExitAnimation(R.anim.alerter_slide_out_to_top)
            .enableSwipeToDismiss()
            .setDuration(alertDuration)
            .show()
}

fun shakeEffect(btn: Button) {
    YoYo.with(Techniques.Tada)
            .duration(shakeEffectDuration)
            .repeat(shakeRepeatCount)
            .playOn(btn)
}