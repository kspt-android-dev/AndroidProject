package com.example.fahrenheit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var adapter: CustomRecyclerAdapter
    lateinit var set: MutableList<String>

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = CustomRecyclerAdapter(this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(baseContext)
        val mTimer = Timer()
        val myTimerTask = MyTimerTask()
        set = mutableListOf()
        var k: Int
        for (i in 1 .. 59){
            k = if (i > 7) i + 2
            else i
            set.add(getString(resources.getIdentifier("label_$k", "string", packageName)))
        }

        mTimer.schedule(myTimerTask, 0,10000)
    }

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            runOnUiThread {
                val label = set[count]
                adapter.push(label)
                recycler_view.smoothScrollToPosition(adapter.itemCount - 1)
            }
            count++
        }
    }
}
