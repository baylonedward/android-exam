package com.kikimore.randomuser.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

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

  fun readableBirthday(): String {
    val dateFormatter = SimpleDateFormat(DEFAULT_FORMAT, Locale.getDefault())
    val date = try {
      dateFormatter.parse(this.birthday)
    } catch (e: Exception) {
      println("String to Date Error: $e")
      null
    }
    val stringFormatter = SimpleDateFormat(READABLE_FORMAT, Locale.getDefault())
    return try {
      if (date != null) stringFormatter.format(date) else this.birthday
    } catch (e: Exception) {
      date?.time.toString()
    }
  }

  companion object {
    private const val DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val READABLE_FORMAT = "MMMM dd, yyyy"
  }
}