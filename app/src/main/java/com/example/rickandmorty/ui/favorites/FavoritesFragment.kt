package com.example.rickandmorty.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.rickandmorty.databinding.FragmentFavoritesBinding
import com.example.rickandmorty.domain.models.Character
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModels<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { list ->
                if (list.isNotEmpty()) {
                    binding.favoritesRecyclerview.adapter = FavoritesAdapter(list) {character -> onClick(character)}
                }
            }
        }
    }

    private fun onClick(character: Character) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(character)
        view?.findNavController()?.navigate(action)
    }
}