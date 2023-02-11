package com.example.rickandmorty.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ListItemResidentBinding
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.Episode
import com.squareup.picasso.Picasso

class ResidentsAdapter(
    private val residents: List<Character>,
    private val clickListener: (Character) -> Unit
) : Adapter<ResidentsAdapter.ResidentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResidentViewHolder {
        val view = ListItemResidentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResidentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResidentViewHolder, position: Int) {
        holder.bind(residents[position])
    }

    override fun getItemCount() = residents.size

    inner class ResidentViewHolder(private val binding: ListItemResidentBinding) : ViewHolder(binding.root) {
        fun bind(resident: Character) {
            binding.apply {
                root.setOnClickListener { clickListener(resident) }

                residentName.text = resident.name
                Picasso.get().load(resident.image).placeholder(R.mipmap.ic_launcher).into(residentImage)
            }
        }
    }
}