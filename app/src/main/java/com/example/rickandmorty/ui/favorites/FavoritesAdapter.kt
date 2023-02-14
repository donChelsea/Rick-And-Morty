package com.example.rickandmorty.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ListItemFavoriteBinding
import com.example.rickandmorty.domain.models.Character
import com.squareup.picasso.Picasso

class FavoritesAdapter(
    private val characters: List<Character>,
    private val clickListener: (Character) -> Unit,
) : Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view = ListItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount() = characters.size

    inner class FavoritesViewHolder(private val binding: ListItemFavoriteBinding) : ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.apply {
                root.setOnClickListener { clickListener(character) }

                Picasso.get().load(character.image).placeholder(R.mipmap.ic_launcher).into(favoriteImage)
                favoriteName.text = character.name
            }
        }
    }
}