package com.example.rickandmorty.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentDetailsBinding
import com.example.rickandmorty.databinding.FragmentHomeBinding
import com.example.rickandmorty.ui.home.HomeViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel by viewModels<DetailsViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.details)
        viewModel.postEvent(DetailsUiEvent.UpdateCharacter(args.character))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                it?.let { character ->
                    binding.apply {
                        val statusLabelColor = if (character.status == "Alive") R.color.alive_green else R.color.dead_red
                        statusLabel.setBackgroundColor(ContextCompat.getColor(requireContext(), statusLabelColor))

                        name.text = character.name
                        statusText.text = character.status
                        originText.text = character.origin.name
                        locationText.text = character.location.name
                        Picasso.get().load(character.image).placeholder(R.mipmap.ic_launcher).into(image)
                    }
                }
            }
        }


    }

}