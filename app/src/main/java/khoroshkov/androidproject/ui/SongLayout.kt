package khoroshkov.androidproject.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import android.widget.TextView
import khoroshkov.androidproject.R
import org.jetbrains.anko.*

class SongLayout(context: Context) : LinearLayout(context) {
    val artist = TextView(context)
    val title = TextView(context)
    init {
        this.addView(title)
        this.addView(artist)
        orientation = VERTICAL
        with(title) {
            textColor = ContextCompat.getColor(context, R.color.colorMainElement)
            textSize = 20F
        }
        with(artist) {
            textColor = ContextCompat.getColor(context, R.color.colorActive)
            textSize = 19F
        }
    }
}