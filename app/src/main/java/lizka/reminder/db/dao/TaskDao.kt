package lizka.reminder.db.dao

import android.arch.persistence.room.*
import lizka.reminder.model.ModelTask


@Dao
interface TaskDao {

    @Query("SELECT * FROM modeltask ")
    fun getAll(): List<ModelTask>

    @Insert
    fun insert(file: ModelTask)

    @Delete
    @Ignore
    fun delete(file: ModelTask)

    @Update
    fun update(file: ModelTask)

    @Query("SELECT * FROM modeltask WHERE id IN (:id)")
    fun getFileById(id:Long):List<ModelTask>

}