package khoroshkov.androidproject.ui

import android.graphics.PorterDuff
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.*
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.exoplayer2.ui.PlayerView
import khoroshkov.androidproject.*
import org.jetbrains.anko.*

class MainActivityUI : AnkoComponent<MainActivity> {
    lateinit var playerView: PlayerView
    lateinit var seekBar: SeekBar
    lateinit var songTimePosition: TextView
    lateinit var songDuration: TextView
    lateinit var songTitle: TextView
    lateinit var songArtist: TextView
    lateinit var previousButton: ImageButton
    lateinit var playButton: ImageButton
    lateinit var nextButton: ImageButton
    lateinit var shuffleButton: ImageButton
    lateinit var playlistButton: ImageButton
    lateinit var repeatButton: ImageButton

    @RequiresApi(Build.VERSION_CODES.M)
    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        verticalLayout {
            relativeLayout {
                playerView = PlayerView(context).apply {
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
                addView(playerView)
            }.lparams {
                width = matchParent
                weight = 200F
            }
            relativeLayout {
                seekBar = seekBar {
                    progressDrawable.setColorFilter(
                        ContextCompat.getColor(context, R.color.colorInactive),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    thumb.setColorFilter(
                        ContextCompat.getColor(context, R.color.colorInactive),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }.lparams {
                    rightPadding = dip(EDGE_PADDING)
                    leftPadding = dip(EDGE_PADDING)
                    width = matchParent
                }
            }.lparams {
                weight = 1F
                width = matchParent
            }
            relativeLayout {
                songTimePosition = textView {
                    text = resources.getString(R.string.time_position_default)
                    textSize = 15F
                    textColor = ContextCompat.getColor(context, R.color.colorActive)
                }.lparams {
                    alignParentLeft()
                    leftPadding = dip(EDGE_PADDING)
                }
                songDuration = textView {
                    text = resources.getString(R.string.duration_default)
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
                songTitle = textView {
                    text = resources.getString(R.string.title_default)
                    textSize = 28F
                    textColor = ContextCompat.getColor(context, R.color.colorMainElement)
                    setHorizontallyScrolling(true)
                    ellipsize = TextUtils.TruncateAt.MARQUEE
                    setSingleLine(true)
                    isSelected = true
                    marqueeRepeatLimit = -1 // Text hide for a long time without this
                    movementMethod = ScrollingMovementMethod()
                }.lparams {
                    centerInParent()
                }
            }.lparams {
                width = matchParent
                weight = 5F
            }
            relativeLayout {
                songArtist = textView {
                    text = resources.getString(R.string.artist_default)
                    textSize = 20F
                    textColor = ContextCompat.getColor(context, R.color.colorActive)
                    setHorizontallyScrolling(true)
                    ellipsize = TextUtils.TruncateAt.MARQUEE
                    setSingleLine(true)
                    isSelected = true
                    marqueeRepeatLimit = -1 // Text hide for a long time without this
                    movementMethod = ScrollingMovementMethod()
                }.lparams {
                    centerInParent()
                }
            }.lparams {
                width = matchParent
                weight = 2F
            }
            relativeLayout {
                previousButton = imageButton {
                    imageResource = R.drawable.ic_previous
                    backgroundColor = 0
                }.lparams(width = wrapContent, height = wrapContent) {
                    leftMargin = dip(BUTTON_PADDING)
                    centerVertically()
                    alignParentLeft()
                }
                playButton = imageButton {
                    imageResource = R.drawable.ic_play
                    backgroundColor = 0
                }.lparams(width = wrapContent, height = wrapContent) {
                    centerInParent()
                }
                nextButton = imageButton {
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
                shuffleButton = imageButton {
                    imageResource = R.drawable.ic_shuffle_off
                    backgroundColor = 0
                    leftPadding = dip(EDGE_PADDING)
                }.lparams {
                    alignParentStart()
                    centerVertically()
                }
                playlistButton = imageButton {
                    imageResource = R.drawable.ic_format_list_bulleted
                    backgroundColor = 0
                }.lparams {
                    centerInParent()
                }
                repeatButton = imageButton {
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