package com.kikimore.randomuser.data.entities.remote

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
data class Result<T>(
  val results: T,
  val info: Info
)

data class Info(
  val seed: String,
  val results: Int,
  val page: Int,
  val version: String
)