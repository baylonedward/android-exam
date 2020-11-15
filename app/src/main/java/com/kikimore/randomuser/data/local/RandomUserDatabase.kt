package com.kikimore.randomuser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kikimore.randomuser.data.entities.local.Person

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
@Database(
  entities = [Person::class],
  version = 1,
  exportSchema = false
)
abstract class RandomUserDatabase : RoomDatabase() {
  abstract fun personDao(): PersonDao

  companion object {
    @Volatile
    private var instance: RandomUserDatabase? = null

    fun getDatabase(context: Context): RandomUserDatabase =
      instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

    private fun buildDatabase(appContext: Context) =
      Room.databaseBuilder(appContext, RandomUserDatabase::class.java, "random_user_db")
        .fallbackToDestructiveMigration()
        .build()
  }
}