package com.kikimore.randomuser.features.person.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.kikimore.randomuser.R
import com.kikimore.randomuser.databinding.FragmentPersonListBinding
import com.kikimore.randomuser.features.person.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PersonListFragment : Fragment() {
  private val viewModel: PersonViewModel by activityViewModels()
  private val listAdapter by lazy { PersonListAdapter(viewModel) }
  private val isTablet by lazy { context?.resources?.getBoolean(R.bool.isTablet) ?: false }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.getPersons()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = FragmentPersonListBinding.inflate(inflater, container, false)
    return view.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val binding = FragmentPersonListBinding.bind(view)
    // set adapter
    binding.personListView.adapter = listAdapter
    // set observers
    setObservers()
    // display detail fragment on Tablet mode
    if (isTablet) displayDetailLayout()
  }

  private fun displayDetailLayout() {
    val navHostFragment =
      childFragmentManager.findFragmentById(R.id.nav_host_fragment_detail) as NavHostFragment
    navHostFragment.navController.navigate(R.id.navigation_detail_fragment)
  }

  private fun setObservers() {
    // list
    viewModel.personsList.onEach {
      if (it == null) return@onEach
      println("Bind: ${listAdapter.itemCount}")
      listAdapter.notifyDataSetChanged()
    }.launchIn(lifecycleScope)
    // info message
    viewModel.infoMessage.onEach {
      if (it == null) return@onEach
      toast(it)
    }.launchIn(lifecycleScope)
  }

  private fun toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)?.show()
  }
}