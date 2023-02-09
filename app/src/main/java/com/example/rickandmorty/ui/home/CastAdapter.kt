package com.example.rickandmorty.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ListItemCastBinding
import com.example.rickandmorty.domain.models.Character
import com.squareup.picasso.Picasso

class CastAdapter(
    private val characters: List<Character>,
    private val context: Context,
    private val clickListener: (Character) -> Unit
) : Adapter<CastAdapter.CastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = ListItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(characters[position])
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_falling_down)
    }

    override fun getItemCount() = characters.size

    inner class CastViewHolder(private val binding: ListItemCastBinding) : ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.apply {
                root.setOnClickListener { clickListener(character) }

                Picasso.get().load(character.image).placeholder(R.mipmap.ic_launcher).into(castImage)
                castName.text = character.name

                val statusDrawable = if (character.status == "Alive") R.drawable.ic_circle_green else R.drawable.ic_circle_red
                statusText.text = character.status
                statusText.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, statusDrawable), null, null, null)
            }
        }
    }
}