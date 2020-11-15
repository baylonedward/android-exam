package com.kikimore.randomuser.data.entities.remote

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
data class User (
  val name: Name,
  val location: Location,
  val email: String,
  val dob: DateOfBirth,
  val cell: String
)

data class Name(
  val first: String,
  val last: String
)

data class Location (
  val city: String,
  val state: String,
  val country: String
)

data class DateOfBirth (
  val date: String,
  val age: Int
)