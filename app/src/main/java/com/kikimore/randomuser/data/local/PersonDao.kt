package com.kikimore.randomuser.data.local

import androidx.room.Dao
import androidx.room.Query
import com.kikimore.randomuser.data.entities.local.Person
import kotlinx.coroutines.flow.Flow

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
@Dao
interface PersonDao : BaseDao<Person> {
  @Query("SELECT * FROM person_table")
  fun all(): Flow<List<Person>>

  @Query("SELECT COUNT(*) FROM person_table")
  fun count(): Flow<Int>
}