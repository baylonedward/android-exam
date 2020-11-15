package com.kikimore.randomuser.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by: ebaylon.
 * Created on: 12/11/2020.
 */
@Entity(tableName = "person_table")
data class Person(
  @PrimaryKey(autoGenerate = true)
  val id: Int,
  val firstName: String,
  val lastName: String,
  val birthday: String,
  val age: Int,
  val email: String,
  val mobile: String,
  val address: String,
  val contactPerson: String,
  val contactPersonPhoneNumber: String
) {
  constructor(
    firstName: String,
    lastName: String,
    birthday: String,
    age: Int,
    email: String,
    mobile: String,
    address: String,
    contactPerson: String,
    contactPersonPhoneNumber: String
  ) : this(
    0,
    firstName,
    lastName,
    birthday,
    age,
    email,
    mobile,
    address,
    contactPerson,
    contactPersonPhoneNumber
  )
}