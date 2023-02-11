package com.example.rickandmorty.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rickandmorty.databinding.ListItemEpisodeBinding
import com.example.rickandmorty.domain.models.Episode

class EpisodesAdapter(
    private val episodes: List<Episode>,
    private val clickListener: (Episode) -> Unit
) : Adapter<EpisodesAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = ListItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount() = episodes.size

    inner class EpisodeViewHolder(private val binding: ListItemEpisodeBinding) : ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            binding.apply {
                root.setOnClickListener { clickListener(episode) }

                episodeName.text = episode.name
                episodeOrder.text = episode.order
            }
        }
    }
}