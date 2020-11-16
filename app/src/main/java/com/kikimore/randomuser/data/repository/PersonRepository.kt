package com.kikimore.randomuser.data.repository

import com.kikimore.randomuser.data.entities.local.Person
import com.kikimore.randomuser.data.local.PersonDao
import com.kikimore.randomuser.data.remote.RandomUserService
import com.kikimore.randomuser.data.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
class PersonRepository(
  private val remoteDataSource: RandomUserService,
  private val localDataSource: PersonDao
) {

  private suspend fun <T> evaluateCall(call: suspend () -> Response<T>): Resource<T> {
    try {
      val response = call()
      if (response.isSuccessful) {
        val body = response.body()
        if (body != null) return Resource.Success(body)
      }
      return Resource.Error(" ${response.code()} ${response.message()}")
    } catch (e: Exception) {
      return Resource.Error(e.message ?: e.toString())
    }
  }

  @ExperimentalCoroutinesApi
  fun getPersons(count: Int = 20): Flow<Resource<List<Person>>> = channelFlow {
    // launch new job to return data from local
    launch {
      localDataSource.all().collect {
        send(Resource.Success(it))
      }
    }
    // invoke api call if person table is empty
    val localCount = localDataSource.count().firstOrNull()
    if (localCount != null && localCount == 0) {
      val results = count.toString()
      val inc = arrayOf("name", "location", "email", "dob", "cell")
      val parameters = mapOf(RESULTS to results, INCLUDE to inc.joinToString())
      when (val response = evaluateCall { remoteDataSource.getRandomUsers(parameters) }) {
        // if success
        is Resource.Success -> {
          // if result not null insert in local data source
          localDataSource.insert(response.data.results.map { it.toPerson() })
        }
        is Resource.Error -> send(Resource.Error(message = response.message))
        else -> {
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