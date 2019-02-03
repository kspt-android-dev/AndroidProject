package khoroshkov.androidproject.ui

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.*
import com.google.android.exoplayer2.ui.PlayerView
import khoroshkov.androidproject.*
import org.jetbrains.anko.*
import kotlin.properties.Delegates

class MainActivityUI : AnkoComponent<MainActivity> {

    companion object {
        /* Maybe it is not a good way but it works */
        var PLAYER_VIEW: PlayerView by Delegates.notNull()
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
                seekBar {
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
                textView {
                    text = START_TIME_DEFAULT
                    textSize = 15F
                    textColor = resources.getColor(R.color.secondColor, null)
                }.lparams {
                    alignParentLeft()
                    leftPadding = dip(EDGE_PADDING)
                }
                textView {
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
                textView {
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
                textView {
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
                imageButton {
                    imageResource = R.drawable.ic_previous
                    backgroundColor = 0
                    rightPadding = dip(160)
                }.lparams {
                    centerInParent()
                }
                imageButton {
                    imageResource = R.drawable.ic_play
                    backgroundColor = 0
                }.lparams {
                    centerInParent()
                }
                imageButton {
                    imageResource = R.drawable.ic_next
                    backgroundColor = 0
                    leftPadding = dip(160)
                }.lparams {
                    centerInParent()
                }
            }.lparams {
                width = matchParent
                weight = 5F
            }
            relativeLayout {
                imageButton {
                    imageResource = R.drawable.ic_shuffle
                    backgroundColor = 0
                    leftPadding = dip(EDGE_PADDING)
                }.lparams {
                    alignParentStart()
                    centerVertically()
                }
                imageButton {
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
