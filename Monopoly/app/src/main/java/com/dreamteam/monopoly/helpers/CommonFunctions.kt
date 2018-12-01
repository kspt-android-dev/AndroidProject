package com.dreamteam.monopoly.helpers

import android.view.View
import com.dreamteam.monopoly.R
import com.muddzdev.styleabletoastlibrary.StyleableToast

fun showToast(v: View, text: String) {
    StyleableToast.makeText(v.context, text, R.style.TinyToast).show()
}