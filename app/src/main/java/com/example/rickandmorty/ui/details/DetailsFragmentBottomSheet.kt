package com.example.rickandmorty.ui.details

import CharacterModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.CharacterQuery
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.DetailsBottomSheetDialogBinding
import com.example.rickandmorty.models.Contract
import com.example.rickandmorty.models.SharedPreferencesModel
import com.example.rickandmorty.ui.favorites.FavoritesPresenter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class DetailsFragmentBottomSheet : BottomSheetDialogFragment(), Contract.View {
    private lateinit var binding: DetailsBottomSheetDialogBinding
    private lateinit var detailsPresenter: DetailsPresenter
    private lateinit var favsPresenter: FavoritesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailsPresenter = DetailsPresenter(this, CharacterModel())
        detailsPresenter.characterId = arguments?.getString(ARG_ID).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailsBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favsPresenter = FavoritesPresenter(this, SharedPreferencesModel(requireContext()))

        lifecycleScope.launchWhenResumed {
            detailsPresenter.getCharacter(detailsPresenter.characterId)
        }
    }

    override fun setData(
        charactersUpdate: List<CharactersQuery.Result?>?,
        characterUpdate: CharacterQuery.Character?,
        savedData: List<Pair<String, Any?>>?
    ) {
        binding.apply {
            characterUpdate?.let { char ->
                name.text = char.name
                species.text = String.format(getString(R.string.species), char.species)
                origin.text = String.format(getString(R.string.origin), char.origin?.name)
                location.text = String.format(getString(R.string.location), char.location?.name)
                Picasso.get().load(char.image).into(image)
                hideProgress()

                favoriteIcon.setOnClickListener {
                    lifecycleScope.launch {
                        favsPresenter.add(detailsPresenter.characterId, char.name.toString())
                        Toast.makeText(requireContext(), "Added ${char.name.toString()} to favorites!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun showError(error: String) {
        hideProgress()
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            infoViewGroup.visibility = View.INVISIBLE
        }
    }

    override fun hideProgress() {
        binding.apply {
            progressBar.visibility = View.GONE
            infoViewGroup.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsPresenter.onDestroy()
    }

    companion object {
        const val TAG = "InfoFragmentBottomSheet"
        private const val ARG_ID = "id"

        fun newInstance(id: String?): DetailsFragmentBottomSheet {
            val infoBottomSheet = DetailsFragmentBottomSheet()
            val bundle = Bundle()
            bundle.putString(ARG_ID, id)
            infoBottomSheet.arguments = bundle
            return infoBottomSheet
        }
    }
}

