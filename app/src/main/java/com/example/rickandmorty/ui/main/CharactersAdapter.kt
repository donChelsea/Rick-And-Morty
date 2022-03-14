package com.example.rickandmorty.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.databinding.ItemViewCharactersBinding
import com.squareup.picasso.Picasso

class CharactersAdapter(
    private val characters: List<CharactersQuery.Result?>,
    private val clickListener: (CharactersQuery.Result?) -> Unit
) :
    RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            ItemViewCharactersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount() = characters.size

    inner class CharactersViewHolder(private val binding: ItemViewCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharactersQuery.Result?) {
            binding.apply {
                characterName.text = character?.name
                Picasso.get().load(character?.image).into(characterImage)
                itemView.setOnClickListener { clickListener(character) }
            }
        }
    }
}