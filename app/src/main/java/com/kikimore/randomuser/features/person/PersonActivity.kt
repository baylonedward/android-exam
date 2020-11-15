package com.kikimore.randomuser.features.person

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.kikimore.randomuser.R
import com.kikimore.randomuser.databinding.ActivityPersonListBinding
import com.kikimore.randomuser.features.person.master.PersonListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PersonActivity : AppCompatActivity() {

  private val viewModel: PersonViewModel by viewModels()
  private val view by lazy { ActivityPersonListBinding.inflate(layoutInflater) }
  private val isTablet by lazy { resources?.getBoolean(R.bool.isTablet) ?: false }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(view.root)
    if (!isTablet)
      viewModel.onSelect
        .onEach {
          if (!it) return@onEach
          findNavController(R.id.nav_host_fragment_person_activity).navigate(
            PersonListFragmentDirections.actionPersonListToDetail()
          )
        }.launchIn(lifecycleScope)
  }
}