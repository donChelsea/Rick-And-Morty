package com.example.rickandmorty.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ItemViewFavoritesBinding

class FavoritesAdapter(
    private val favorites: List<Pair<String, Any?>>,
    private val clickListener: (Pair<String, Any?>) -> Unit
) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            ItemViewFavoritesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount() = favorites.size

    inner class FavoritesViewHolder(private val binding: ItemViewFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Pair<String, Any?>) {
            binding.apply {
                characterName.text = character.second.toString()
                itemView.setOnClickListener { clickListener(character) }
            }
        }
    }
}