package com.kikimore.randomuser.features.person.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kikimore.randomuser.R
import com.kikimore.randomuser.databinding.ItemPersonListBinding

/**
 * Created by: ebaylon.
 * Created on: 15/11/2020.
 */
class PersonListAdapter(private val personListItemInterface: PersonListItemInterface) :
  RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>() {

  override fun getItemCount(): Int = personListItemInterface.getCount()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.item_person_list, parent, false)
    return PersonViewHolder(view)
  }

  override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
    holder.onBind(
      personListItemInterface.getName(position),
      personListItemInterface.getEmail(position),
      personListItemInterface.getPhone(position),
      personListItemInterface.getOnClick(position)
    )
  }

  interface PersonListItemInterface {
    fun getCount(): Int
    fun getName(position: Int): String
    fun getEmail(position: Int): String
    fun getPhone(position: Int): String
    fun getOnClick(position: Int): () -> Unit
  }

  inner class PersonViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {
    fun onBind(
      name: String,
      email: String,
      phone: String,
      onClick: () -> Unit
    ) {
      ItemPersonListBinding.bind(itemView).apply {
        this.nameTextView.text = name
        this.emailTextView.text = email
        this.mobileTextView.text = phone
        this.personCardView.setOnClickListener { onClick() }
      }
    }
  }
}