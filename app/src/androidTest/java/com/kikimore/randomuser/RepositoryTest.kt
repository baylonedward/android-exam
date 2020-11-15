package com.kikimore.randomuser

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kikimore.randomuser.data.local.RandomUserDatabase
import com.kikimore.randomuser.data.remote.RandomUserService
import com.kikimore.randomuser.data.repository.PersonRepository
import com.kikimore.randomuser.data.utils.LoggingInterceptor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
@RunWith(AndroidJUnit4::class)
class RepositoryTest {
  private lateinit var testSetup: TestSetup

  @Before
  fun setup() {
    testSetup = TestSetup()
  }

  @ExperimentalCoroutinesApi
  @Test
  fun testGetPersons() {
    runBlocking {
      val count = 5
      launch {
        testSetup.personRepository.getPersons(count)
          .collect {
            if (it.isNotEmpty()) {
              assertEquals(it.size, count)
              cancel()
            }
          }
      }
    }
  }
}

private class TestSetup {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val db = Room.inMemoryDatabaseBuilder(context, RandomUserDatabase::class.java).build()
  private val baseUrl = "https://randomuser.me"
  private val okHttpClient = OkHttpClient.Builder().addInterceptor(LoggingInterceptor()).build()
  private val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(MoshiConverterFactory.create())
    .client(okHttpClient)
    .build()
  private val randomUserService = retrofit.create(RandomUserService::class.java)

  val personRepository = PersonRepository.getInstance(
    remoteDataSource = randomUserService,
    localDataSource = db.personDao()
  )
}