package sample.avightclav.checkers.activities

import androidx.appcompat.app.AppCompatActivity
import sample.avightclav.checkers.R

class GameFragmentActivity: AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_create_custom_field)
    }
}