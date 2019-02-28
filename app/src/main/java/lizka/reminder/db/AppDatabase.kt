package lizka.reminder.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import lizka.reminder.db.dao.TaskDao
import lizka.reminder.model.ModelTask


@Database(entities = [ModelTask::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}