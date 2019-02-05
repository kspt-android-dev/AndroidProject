package lestwald.insectkiller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager

class LeaderBoardActivity : AppCompatActivity() {

    private val fragmentEasy = PageFragment()
    private val fragmentMedium = PageFragment()
    private val fragmentHard = PageFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.offscreenPageLimit = 2
        setupViewPager(viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        for (difficulty in 1..3) {
            val dbName = "leaderboard$difficulty"
            val dbHelper = DBHelper(this, dbName)
            val database = dbHelper.readableDatabase
            var c = database.rawQuery("SELECT * FROM $dbName Order By cast(${DBHelper.KEY_SCORE} as INTEGER) DESC", null)
            if (!c.moveToFirst()) {
                addDefaultScores(this, dbName)
                c = database.rawQuery("SELECT * FROM $dbName Order By cast(${DBHelper.KEY_SCORE} as INTEGER) DESC", null)
            }
            var namesStr = ""
            var scoresStr = ""
            var i = 1
            c.moveToFirst()
            val nameColIndex = c.getColumnIndex(DBHelper.KEY_NAME)
            val scoreColIndex = c.getColumnIndex(DBHelper.KEY_SCORE)
            do {
                namesStr = namesStr + '\n' + c.getString(nameColIndex)
                scoresStr = scoresStr + '\n' + c.getInt(scoreColIndex).toString()
                i++
            } while (c.moveToNext() && i <= 10)
            c.close()
            viewPager.post {
                when (difficulty) {
                    1 -> {
                        fragmentEasy.namesTextView.text = namesStr
                        fragmentEasy.scoresTextView.text = scoresStr
                    }
                    2 -> {
                        fragmentMedium.namesTextView.text = namesStr
                        fragmentMedium.scoresTextView.text = scoresStr
                    }
                    3 -> {
                        fragmentHard.namesTextView.text = namesStr
                        fragmentHard.scoresTextView.text = scoresStr
                    }
                }

            }
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(fragmentEasy, getString(R.string.easy))
        adapter.addFragment(fragmentMedium, getString(R.string.medium))
        adapter.addFragment(fragmentHard, getString(R.string.hard))
        viewPager.adapter = adapter
    }
}
