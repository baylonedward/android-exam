package com.kikimore.randomuser.features.person.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kikimore.randomuser.data.entities.local.Person
import com.kikimore.randomuser.databinding.FragmentPersonDetailBinding
import com.kikimore.randomuser.features.person.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PersonDetailFragment : Fragment() {
  private val viewModel: PersonViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = FragmentPersonDetailBinding.inflate(inflater, container, false)
    return view.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    // observe selected person
    viewModel.selectedPerson.onEach {
      if (it == null) return@onEach
      setDetails(it, FragmentPersonDetailBinding.bind(view))
    }.launchIn(lifecycleScope)
  }

  private fun setDetails(person: Person, binding: FragmentPersonDetailBinding) {
    // name
    binding.nameTextView.text = "${person.firstName} ${person.lastName}"
    // birthday
    binding.birthdayTextView.text = "Birthday: ${person.readableBirthday()}"
    // age
    binding.ageTextView.text = "Age: ${person.age}"
    // email
    binding.emailTextView.text = "Email: ${person.email}"
    // phone
    binding.phoneTextView.text = "Phone: ${person.mobile}"
    // address
    binding.addressTextView.text = "Address: ${person.address}"
    // contact person
    binding.contactPersonTextView.text = person.contactPerson
    // contact person number
    binding.contactPersonNumberTextView.text = person.contactPersonPhoneNumber
  }
}