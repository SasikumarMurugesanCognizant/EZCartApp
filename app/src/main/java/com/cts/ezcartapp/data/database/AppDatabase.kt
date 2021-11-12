package com.cts.ezcartapp.data.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.cts.ezcartapp.data.entities.*

@Database(entities = [UserData::class,ShoppingData::class,CartData::class,FeedBackData::class,OrdersData::class], version = 1)
@TypeConverters(ShoppingDataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ezDAO(): EZDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "AppDatabase").allowMainThreadQueries().build()
        }

    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }


}