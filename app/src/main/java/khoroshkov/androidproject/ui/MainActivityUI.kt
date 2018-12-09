package khoroshkov.androidproject.ui

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.*
import khoroshkov.androidproject.*
import org.jetbrains.anko.*

class MainActivityUI : AnkoComponent<MainActivity> {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        verticalLayout {
            relativeLayout {
                imageView {
                    imageResource = R.drawable.pic_example
                }.lparams {
                    width = matchParent
                    height = matchParent
                    rightPadding = dip(EDGE_PADDING)
                    leftPadding = dip(EDGE_PADDING)
                    bottomPadding = dip(EDGE_PADDING)
                    topPadding = dip(EDGE_PADDING)
                    centerInParent()
                }
            }.lparams {
                width = matchParent
                weight = 350F
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
                    text = "0:00"
                    textSize = 15F
                    textColor = resources.getColor(R.color.secondColor, null)
                }.lparams {
                    alignParentLeft()
                    leftPadding = dip(EDGE_PADDING)
                }
                textView {
                    text = "1:11"
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
                    text = "Track"
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
                    text = "Author"
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
                    imageResource = R.drawable.ic_list
                    backgroundColor = 0
                }.lparams {
                    centerInParent()
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
}