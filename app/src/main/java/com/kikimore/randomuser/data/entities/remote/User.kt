package com.kikimore.randomuser.data.entities.remote

import com.kikimore.randomuser.data.entities.local.Person

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
data class User(
  val name: Name,
  val location: Location,
  val email: String,
  val dob: DateOfBirth,
  val cell: String
) {

  private fun getRandomContactPerson(): ContactPerson {
    return arrayOf(
      ContactPerson(
        name = "Cris Tucker",
        contact = "092238391222"
      ),
      ContactPerson(
        name = "Anna Remna",
        contact = "09932103123"
      ),
      ContactPerson(
        name = "Jericho Gobota",
        contact = "09932220101"
      ),
      ContactPerson(
        name = "Renna Amillo",
        contact = "09768389222"
      )
    ).random()
  }

  fun toPerson(): Person {
    val contactPerson = getRandomContactPerson()
    return Person(
      firstName = this.name.first,
      lastName = this.name.last,
      birthday = this.dob.date,
      age = this.dob.age,
      email = this.email,
      mobile = this.cell,
      address = this.location.toString(),
      contactPerson = contactPerson.name,
      contactPersonPhoneNumber = contactPerson.contact
    )
  }
}

data class Name(
  val first: String,
  val last: String
)

data class Location(
  val city: String,
  val state: String,
  val country: String
) {
  override fun toString(): String {
    return "$city $state, $country"
  }
}

data class DateOfBirth(
  val date: String,
  val age: Int
)

data class ContactPerson(
  val name: String,
  val contact: String
)