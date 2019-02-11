package khoroshkov.androidproject.ui

import android.graphics.PorterDuff
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.ListView
import khoroshkov.androidproject.PlaylistActivity
import khoroshkov.androidproject.R
import org.jetbrains.anko.*

class PlaylistActivityUI : AnkoComponent<PlaylistActivity> {
    lateinit var listView: ListView
    lateinit var playAllButton: Button

    @RequiresApi(Build.VERSION_CODES.M)
    override fun createView(ui: AnkoContext<PlaylistActivity>): View = with(ui) {
        verticalLayout {
            textView {
                text = resources.getString(R.string.playlist_title)
                textColor = ContextCompat.getColor(context, R.color.colorMainElement)
                textSize = 25F
                rightPadding = dip(EDGE_PADDING)
                leftPadding = dip(EDGE_PADDING)
                topPadding = dip(EDGE_PADDING)
                bottomPadding = dip(EDGE_PADDING)
            }
            playAllButton = button {
                text = resources.getString(R.string.play_all)
                textSize = 20F
                textColor = ContextCompat.getColor(context, R.color.colorBackground)
                setBackgroundColor(ContextCompat.getColor(context, R.color.colorMainElement))
                width = matchParent
                height = wrapContent
            }
            listView = listView {
                dividerHeight = dip(2)
                divider?.setColorFilter(
                    ContextCompat.getColor(context, R.color.colorInactive),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }
    }
}