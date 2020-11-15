package com.kikimore.randomuser.features.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kikimore.randomuser.data.entities.local.Person
import com.kikimore.randomuser.data.repository.PersonRepository
import com.kikimore.randomuser.features.person.master.PersonListAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
@ExperimentalCoroutinesApi
class PersonViewModel @ViewModelInject constructor(private val personRepository: PersonRepository) :
  ViewModel(), PersonListAdapter.PersonListItemInterface {

  val personsList = MutableStateFlow<List<Person>?>(null)
  val errorMessage = MutableStateFlow<String?>(null)
  val selectedPerson = MutableStateFlow<Person?>(null)
  val onSelect = MutableStateFlow(false)

  private fun getPerson(position: Int) = personsList.value?.get(position)

  fun getPersons() {
    personRepository.getPersons()
      .distinctUntilChanged()
      .catch { errorMessage.value = it.message }
      .onEach { persons ->
        persons.sortedBy { it.firstName }.also {
          personsList.value = it
          if (!it.isNullOrEmpty()) selectedPerson.value = it[0]
        }
      }.launchIn(viewModelScope)
  }

  /**
   * Person List Interface
   */
  override fun getCount(): Int = personsList.value?.size ?: 0

  override fun getName(position: Int): String {
    val person = getPerson(position)
    return "${person?.firstName} ${person?.lastName}"
  }

  override fun getEmail(position: Int): String {
    return "${getPerson(position)?.email}"
  }

  override fun getPhone(position: Int): String {
    return "${getPerson(position)?.mobile}"
  }

  override fun getOnClick(position: Int): () -> Unit = {
    println("Name: ${getPerson(position)?.firstName}")
    selectedPerson.value = getPerson(position)
    onSelect.value = true
    onSelect.value = false
  }
}