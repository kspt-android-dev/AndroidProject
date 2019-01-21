package sample.avightclav.checkers.activities

import androidx.appcompat.app.AppCompatActivity
import sample.avightclav.checkers.R

class CreateCustomActivity: AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        setContentView(R.layout.vertical_create_custom_field)
    }
}