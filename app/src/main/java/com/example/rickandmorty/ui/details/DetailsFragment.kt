package com.example.rickandmorty.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentDetailsBinding
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.Episode
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel by viewModels<DetailsViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.details)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            args.character.let { character ->
                if (character.episodes.orEmpty().contains(",")) {
                    viewModel.postEvent(DetailsUiEvent.GetAllEpisodeAppearances(episodesString = character.episodes.orEmpty()))
                } else {
                    viewModel.postEvent(DetailsUiEvent.GetSingleEpisodeAppearance(episode = character.episodes.orEmpty()))
                }
                character.location?.id?.let {
                    viewModel.postEvent(DetailsUiEvent.GetLocation(it))
                }
                binding.apply {
                    val statusLabelColor = if (character.status == "Alive") R.color.alive_green else R.color.dead_red
                    statusLabel.setBackgroundColor(ContextCompat.getColor(requireContext(), statusLabelColor))

                    name.text = character.name
                    statusText.text = character.status
                    originText.text = character.origin?.name
                    locationText.text = character.location?.name
                    Picasso.get().load(character.image).placeholder(R.mipmap.ic_launcher).into(image)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                binding.appearancesRecyclerview.adapter = EpisodesAdapter(state.episodes) { episode -> onEpisodesClick(episode) }
                binding.residentsRecyclerview.adapter = ResidentsAdapter(state.residents) { resident -> onResidentClick(resident) }
            }
        }
    }

    private fun onResidentClick(resident: Character) {
        println(resident.name)
    }

    private fun onEpisodesClick(episode: Episode) {
        println(episode.name)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_to_favorites -> {
                Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {}
        }
        return false
    }
}