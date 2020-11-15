package com.kikimore.randomuser.data.remote

import com.kikimore.randomuser.data.entities.remote.Result
import com.kikimore.randomuser.data.entities.remote.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
interface RandomUserService {

  @GET("api")
  suspend fun getRandomUsers(
    @QueryMap parameters: Map<String, String>
  ): Response<Result<List<User>>>
}