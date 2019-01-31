package com.example.fahrenheit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(val mainActivity: MainActivity) :
    RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder>() {

    private lateinit var labelView: View

    private var list = mutableListOf<GameCase>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        labelView = LayoutInflater.from(parent.context).inflate(R.layout.custom_card_view, parent, false)
        return CustomViewHolder(labelView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun push(gameCase: GameCase) {
        list.add(gameCase)
        notifyDataSetChanged()
    }

    inner class CustomViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        private val cardView = view.findViewById<CardView>(R.id.card_view)
        private val constraint = view.findViewById<ConstraintLayout>(R.id.constraint_custom_card_view)
        private val textView = view.findViewById<TextView>(R.id.textLabel)!!
        private val answer1 = view.findViewById<TextView>(R.id.answer_1)
        private val answer2 = view.findViewById<TextView>(R.id.answer_2)
        private val answer3 = view.findViewById<TextView>(R.id.answer_3)
        fun onBind(position: Int) {
            isLightMode()
            val anim = AlphaAnimation(0.4f, 1.0f)
            anim.duration = 1000
            anim.startOffset = 20
            anim.repeatMode = Animation.REVERSE
            anim.repeatCount = Animation.INFINITE
            val gameCase = list[position]
            when (gameCase.type) {
                TypeCase.BUTTON_TEXT -> {
                    textView.visibility = View.GONE
                    answer1.visibility = View.VISIBLE
                    answer2.visibility = View.VISIBLE
                    val buttonCase = gameCase as ButtonCase
                    answer1.text = buttonCase.buttonEvents[0].first
                    answer2.text = buttonCase.buttonEvents[1].first
                    if (buttonCase.buttonEvents.size == 3) {
                        answer3.visibility = View.VISIBLE
                        answer3.text = buttonCase.buttonEvents[2].first
                    } else {
                        answer3.visibility = View.GONE
                    }
                    if (position == list.size - 1) {
                        answer1.startAnimation(anim)
                        answer2.startAnimation(anim)
                        answer3.startAnimation(anim)
                        answer1.setOnClickListener {
                            mainActivity.event(buttonCase.buttonEvents[0].second)
                        }
                        answer2.setOnClickListener {
                            mainActivity.event(buttonCase.buttonEvents[1].second)
                        }
                        if (buttonCase.buttonEvents.size == 3) {
                            answer3.setOnClickListener {
                                mainActivity.event(buttonCase.buttonEvents[2].second)
                            }
                        }
                    } else {
                        textView.visibility = View.GONE
                        answer1.visibility = View.GONE
                        answer2.visibility = View.GONE
                        answer3.visibility = View.GONE
                    }
                }
                TypeCase.QUESTION -> {
                    clearAnswerAnimation()
                    val questionCase = gameCase as QuestionCase
                    textView.visibility = View.VISIBLE
                    textView.text = questionCase.text
                    hideAnswer()
                }
                TypeCase.TEXT -> {
                    clearAnswerAnimation()
                    val textCase = gameCase as TextCase
                    textView.visibility = View.VISIBLE
                    textView.text = textCase.text
                    hideAnswer()
                }
                else -> {
                }
            }
        }

        private fun isLightMode() {
            if (mainActivity.isLight) {
                cardView.setBackgroundColor(Color.WHITE)
                constraint.setBackgroundColor(Color.WHITE)
                textView.setBackgroundColor(Color.WHITE)
                textView.setTextColor(Color.BLACK)
                answer1.setTextColor(Color.BLACK)
                answer2.setTextColor(Color.BLACK)
                answer3.setTextColor(Color.BLACK)
            } else {
                cardView.setBackgroundColor(Color.BLACK)
                constraint.setBackgroundColor(Color.BLACK)
                textView.setBackgroundColor(Color.BLACK)
                textView.setTextColor(Color.WHITE)
                answer1.setTextColor(Color.WHITE)
                answer2.setTextColor(Color.WHITE)
                answer3.setTextColor(Color.WHITE)
            }
        }

        private fun clearAnswerAnimation() {
            answer1.clearAnimation()
            answer2.clearAnimation()
            answer3.clearAnimation()
        }

        private fun hideAnswer() {
            answer1.visibility = View.GONE
            answer2.visibility = View.GONE
            answer3.visibility = View.GONE
        }
    }
}