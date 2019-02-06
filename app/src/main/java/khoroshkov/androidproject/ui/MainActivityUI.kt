package khoroshkov.androidproject.ui

import android.graphics.PorterDuff
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.exoplayer2.ui.PlayerView
import khoroshkov.androidproject.*
import khoroshkov.androidproject.utils.*
import org.jetbrains.anko.*
import kotlin.properties.Delegates

class MainActivityUI : AnkoComponent<MainActivity> {

    companion object {
        /* Maybe it is not a good way but it works */
        var PLAYER_VIEW: PlayerView by Delegates.notNull()
        var SEEK_BAR: SeekBar by Delegates.notNull()
        var TIME_POSITION: TextView by Delegates.notNull()
        var DURATION: TextView by Delegates.notNull()
        var TITLE: TextView by Delegates.notNull()
        var ARTIST: TextView by Delegates.notNull()
        var PREVIOUS_BUTTON: ImageButton by Delegates.notNull()
        var PLAY_BUTTON: ImageButton by Delegates.notNull()
        var NEXT_BUTTON: ImageButton by Delegates.notNull()
        var SHUFFLE_BUTTON: ImageButton by Delegates.notNull()
        var LIST_BUTTON: ImageButton by Delegates.notNull()
        var REPEAT_BUTTON: ImageButton by Delegates.notNull()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        verticalLayout {
            relativeLayout {
                PLAYER_VIEW = PlayerView(context).apply {
                    lparams {
                        width = matchParent
                        height = matchParent
                        rightPadding = dip(EDGE_PADDING)
                        leftPadding = dip(EDGE_PADDING)
                        bottomPadding = dip(EDGE_PADDING)
                        topPadding = dip(EDGE_PADDING)
                        centerInParent()
                    }
                    useController = false
                    setShutterBackgroundColor(ContextCompat.getColor(context, R.color.colorBackground))
                    defaultArtwork = ContextCompat.getDrawable(context, R.drawable.ic_audiotrack)
                }
                addView(PLAYER_VIEW)
            }.lparams {
                width = matchParent
                weight = 200F
            }
            relativeLayout {
                SEEK_BAR = seekBar().apply {
                    progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorInactive), PorterDuff.Mode.SRC_ATOP)
                    thumb.setColorFilter(ContextCompat.getColor(context, R.color.colorInactive), PorterDuff.Mode.SRC_ATOP)
                    lparams {
                        rightPadding = dip(EDGE_PADDING)
                        leftPadding = dip(EDGE_PADDING)
                        width = matchParent
                    }
                }
            }.lparams {
                weight = 1F
                width = matchParent
            }
            relativeLayout {
                TIME_POSITION = textView {
                    text = TIME_POSITION_DEFAULT
                    textSize = 15F
                    textColor = ContextCompat.getColor(context, R.color.colorActive)
                }.lparams {
                    alignParentLeft()
                    leftPadding = dip(EDGE_PADDING)
                }
                DURATION = textView {
                    text = DURATION_DEFAULT
                    textSize = 15F
                    textColor = ContextCompat.getColor(context, R.color.colorActive)
                }.lparams {
                    alignParentRight()
                    rightPadding = dip(EDGE_PADDING)
                }
            }.lparams {
                weight = 1F
            }
            relativeLayout {
                TITLE = textView {
                    text = TITLE_DEFAULT
                    textSize = 28F
                    textColor = ContextCompat.getColor(context, R.color.colorMainElement)
                }.lparams {
                    centerInParent()
                }
            }.lparams {
                width = matchParent
                weight = 5F
            }
            relativeLayout {
                ARTIST = textView {
                    text = ARTIST_DEFAULT
                    textSize = 20F
                    textColor = ContextCompat.getColor(context, R.color.colorActive)
                }.lparams {
                    centerInParent()
                }
            }.lparams {
                width = matchParent
                weight = 2F
            }
            relativeLayout {
                PREVIOUS_BUTTON = imageButton {
                    imageResource = R.drawable.ic_previous
                    backgroundColor = 0
                }.lparams(width = wrapContent, height = wrapContent) {
                    leftMargin = dip(BUTTON_PADDING)
                    centerVertically()
                    alignParentLeft()
                }
                PLAY_BUTTON = imageButton {
                    imageResource = R.drawable.ic_play
                    backgroundColor = 0
                }.lparams(width = wrapContent, height = wrapContent) {
                    centerInParent()
                }
                NEXT_BUTTON = imageButton {
                    imageResource = R.drawable.ic_next
                    backgroundColor = 0
                }.lparams(width = wrapContent, height = wrapContent) {
                    rightMargin = dip(BUTTON_PADDING)
                    centerVertically()
                    alignParentRight()
                }
            }.lparams {
                width = matchParent
                weight = 5F
            }
            relativeLayout {
                SHUFFLE_BUTTON = imageButton {
                    imageResource = R.drawable.ic_shuffle_off
                    backgroundColor = 0
                    leftPadding = dip(EDGE_PADDING)
                }.lparams {
                    alignParentStart()
                    centerVertically()
                }
                LIST_BUTTON = imageButton {
                    imageResource = R.drawable.ic_format_list_bulleted
                    backgroundColor = 0
                }.lparams {
                    centerInParent()
                }
                REPEAT_BUTTON = imageButton {
                    imageResource = R.drawable.ic_repeat_off
                    backgroundColor = 0
                    rightPadding = dip(EDGE_PADDING)
                }.lparams {
                    centerVertically()
                    alignParentEnd()
                }
            }.lparams {
                weight = 1F
            }
        }
    }
}