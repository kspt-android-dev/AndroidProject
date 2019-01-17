package sample.avightclav.checkers

import androidx.room.*
import android.content.Context
import java.io.*


class GameboardDBConverters {

    @TypeConverter
    fun fromGameboard(gameboard: Gameboard): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream();
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(gameboard)
        objectOutputStream.flush()
        return byteArrayOutputStream.toByteArray()
    }

    @TypeConverter
    fun toGameboard(byteArray: ByteArray): Gameboard {
        return ObjectInputStream(byteArray.inputStream()).readObject() as Gameboard
    }
}

@Entity
data class GameboardDBEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo var gameboard: Gameboard)

@Dao
interface GameboardDAO {

    @Query ("SELECT * FROM GameboardDBEntity ORDER BY id DESC LIMIT 1")
    fun getLast(): GameboardDBEntity

    @Insert
    fun insertAll(vararg gameboardDBEntity: GameboardDBEntity)

    @Delete
    fun delete(gameboardDBEntity: GameboardDBEntity)
}

@Database(entities = [GameboardDBEntity::class], version = 1, exportSchema = false)
@TypeConverters(GameboardDBConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun gameboardDAO(): GameboardDAO

    companion object {
        private val DB_NAME = "GameboardDBEntity"
        @Volatile var appDatabase_INSTANCE: AppDatabase? = null

        @Synchronized
        fun get(context: Context): AppDatabase {
            if (appDatabase_INSTANCE == null)
                appDatabase_INSTANCE = create(context)

            return appDatabase_INSTANCE as AppDatabase
        }

        private fun create(context: Context): AppDatabase {
            val b = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            b.allowMainThreadQueries()
            return b.build()
        }
    }
}