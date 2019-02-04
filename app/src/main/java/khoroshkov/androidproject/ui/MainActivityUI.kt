package khoroshkov.androidproject.ui

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.*
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.exoplayer2.ui.PlayerView
import khoroshkov.androidproject.*
import org.jetbrains.anko.*
import kotlin.properties.Delegates

class MainActivityUI : AnkoComponent<MainActivity> {

    companion object {
        /* Maybe it is not a good way but it works */
        var PLAYER_VIEW: PlayerView by Delegates.notNull()
        var SEEK_BAR: SeekBar by Delegates.notNull()
        var CURRENT_TIME: TextView by Delegates.notNull()
        var DURATION: TextView by Delegates.notNull()
        var TRACK: TextView by Delegates.notNull()
        var ARTIST: TextView by Delegates.notNull()
        var PREVIOUS_BUTTON: ImageButton by Delegates.notNull()
        var PLAY_BUTTON: ImageButton by Delegates.notNull()
        var NEXT_BUTTON: ImageButton by Delegates.notNull()
        var SHUFFLE_BUTTON: ImageButton by Delegates.notNull()
        var REPEAT_BUTTON: ImageButton by Delegates.notNull()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        verticalLayout {
            relativeLayout {
                PLAYER_VIEW = playerView(context).apply {
                    lparams {
                        width = matchParent
                        height = matchParent
                        rightPadding = dip(EDGE_PADDING)
                        leftPadding = dip(EDGE_PADDING)
                        bottomPadding = dip(EDGE_PADDING)
                        topPadding = dip(EDGE_PADDING)
                        centerInParent()
                    }
                    /* Does not work */
                    //defaultArtwork = ResourcesCompat.getDrawable(resources, R.drawable.ic_audiotrack, null)
                }
            }.lparams {
                width = matchParent
                weight = 200F
            }
            relativeLayout {
                SEEK_BAR = seekBar().lparams {
                    rightPadding = dip(EDGE_PADDING)
                    leftPadding = dip(EDGE_PADDING)
                    width = matchParent
                }
            }.lparams {
                weight = 1F
                width = matchParent
            }
            relativeLayout {
                CURRENT_TIME = textView {
                    text = START_TIME_DEFAULT
                    textSize = 15F
                    textColor = resources.getColor(R.color.secondColor, null)
                }.lparams {
                    alignParentLeft()
                    leftPadding = dip(EDGE_PADDING)
                }
                DURATION = textView {
                    text = END_TIME_DEFAULT
                    textSize = 15F
                    textColor = resources.getColor(R.color.secondColor, null)
                }.lparams {
                    alignParentRight()
                    rightPadding = dip(EDGE_PADDING)
                }
            }.lparams {
                weight = 1F
            }
            relativeLayout {
                TRACK = textView {
                    text = TRACK_NAME_DEFAULT
                    textSize = 28F
                    textColor = resources.getColor(R.color.firstColor, null)
                }.lparams {
                    centerInParent()
                }
            }.lparams {
                width = matchParent
                weight = 5F
            }
            relativeLayout {
                ARTIST = textView {
                    text = AUTHOR_NAME_DEFAULT
                    textSize = 20F
                    textColor = resources.getColor(R.color.secondColor, null)
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
                    imageResource = R.drawable.ic_shuffle
                    backgroundColor = 0
                    leftPadding = dip(EDGE_PADDING)
                }.lparams {
                    alignParentStart()
                    centerVertically()
                }
                REPEAT_BUTTON = imageButton {
                    imageResource = R.drawable.ic_repeat
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

    private fun ViewManager.playerView(context: Context) = PlayerView(context)
}