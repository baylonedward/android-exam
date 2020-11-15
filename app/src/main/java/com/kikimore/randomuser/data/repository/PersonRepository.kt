package com.kikimore.randomuser.data.repository

import android.util.Log
import com.kikimore.randomuser.data.entities.local.Person
import com.kikimore.randomuser.data.local.PersonDao
import com.kikimore.randomuser.data.remote.RandomUserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
class PersonRepository(
  private val remoteDataSource: RandomUserService,
  private val localDataSource: PersonDao
) {

  @ExperimentalCoroutinesApi
  fun getPersons(count: Int = 20): Flow<List<Person>> = channelFlow {
    // launch new job to return data from local
    launch {
      localDataSource.all().collect {
        Log.i("local data size", "${it.size}")
        send(it)
      }
    }
    // invoke api call if person table is empty
    val localCount = localDataSource.count().firstOrNull()
    if (localCount != null && localCount == 0) {
      val results = count.toString()
      val inc = arrayOf("name", "location", "email", "dob", "cell")
      val parameters = mapOf(RESULTS to results, INCLUDE to inc.joinToString())
      remoteDataSource.getRandomUsers(parameters).also { request ->
        // if success
        if (request.isSuccessful) {
          request.body()?.results?.also { users ->
            // if result not null insert in local data source
            localDataSource.insert(users.map { it.toPerson() })
          }
        }
      }
    }
  }.flowOn(Dispatchers.IO)

  companion object {
    private const val RESULTS = "results"
    private const val INCLUDE = "inc"

    @Volatile
    private var instance: PersonRepository? = null
    fun getInstance(
      remoteDataSource: RandomUserService,
      localDataSource: PersonDao
    ) = instance ?: synchronized(this) {
      PersonRepository(remoteDataSource, localDataSource).also { instance = it }
    }
  }
}