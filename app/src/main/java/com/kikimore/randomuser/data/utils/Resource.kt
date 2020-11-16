package com.kikimore.randomuser.data.utils

/**
 * Created by: ebaylon.
 * Created on: 16/11/2020.
 */
sealed class Resource<out T> {
  data class Success<T>(val data: T, val message: String? = null) : Resource<T>()
  data class Error(val message: String? = null) : Resource<Nothing>()
  data class Loading(val message: String? = null) : Resource<Nothing>()
}