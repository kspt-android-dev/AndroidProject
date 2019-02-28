package lizka.reminder

import android.app.Application
import android.arch.persistence.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lizka.reminder.db.AppDatabase

class ReminderApplication : Application(){


    lateinit var db: AppDatabase
    lateinit var mainActivity: MainActivity

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database").build()
        GlobalScope.launch(Dispatchers.Main) {

        }
    }

    fun addActivity(activity: MainActivity) {
        this.mainActivity = activity
    }
}