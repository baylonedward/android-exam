package com.kikimore.randomuser

import com.kikimore.randomuser.data.remote.RandomUserService
import com.kikimore.randomuser.data.utils.LoggingInterceptor
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */

@RunWith(JUnit4::class)
class ApiTest {
  private val baseUrl = "https://randomuser.me/"
  private lateinit var okHttpClient: OkHttpClient
  private lateinit var retrofit: Retrofit
  private lateinit var randomUserService: RandomUserService

  @Before
  fun setup() {
    okHttpClient = OkHttpClient.Builder().addInterceptor(LoggingInterceptor()).build()
    retrofit = Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(MoshiConverterFactory.create())
      .client(okHttpClient)
      .build()
    randomUserService = retrofit.create(RandomUserService::class.java)
  }

  @Test
  fun testGetRandomUsers() {
    val results = "5"
    val seed = "54b20ffa610d1b48"
    val inc = arrayOf("name", "location", "email", "dob", "cell")
    val parameters = mapOf(RESULTS to results, SEED to seed, INCLUDE to inc.joinToString())
    runBlocking {
      val request = randomUserService.getRandomUsers(parameters)
      // test result is not null
      assertNotNull(request.body()?.results)
      // test result size
      assertEquals(request.body()?.results?.size, results.toInt())
    }
  }

  companion object {
    private const val RESULTS = "results"
    private const val SEED = "seed"
    private const val INCLUDE = "inc"
  }
}