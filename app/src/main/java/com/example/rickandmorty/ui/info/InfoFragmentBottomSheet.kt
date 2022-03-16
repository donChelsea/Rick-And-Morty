package com.example.rickandmorty.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.*
import com.example.rickandmorty.databinding.InfoBottomSheetDialogBinding
import com.example.rickandmorty.ui.favorites.FavoritesPresenter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class InfoFragmentBottomSheet : BottomSheetDialogFragment(), Contract.View {
    private lateinit var binding: InfoBottomSheetDialogBinding
    private lateinit var infoPresenter: InfoPresenter
    private lateinit var favPresenter: FavoritesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        infoPresenter = InfoPresenter(this, CharacterModel())
        infoPresenter.characterId = arguments?.getString(ARG_ID).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InfoBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favPresenter = FavoritesPresenter(this, SharedPreferencesModel(requireContext()))

        lifecycleScope.launchWhenStarted {
            infoPresenter.getCharacter(infoPresenter.characterId)
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
                    lifecycleScope.launchWhenResumed {
                        favPresenter.add(infoPresenter.characterId, char.name.toString())
                        Toast.makeText(requireContext(), "Added ${char.name.toString()} to favorites!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun showError(error: String) {
        showProgress()
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
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
        infoPresenter.onDestroy()
    }

    companion object {
        const val TAG = "InfoFragmentBottomSheet"
        private const val ARG_ID = "id"

        fun newInstance(id: String?): InfoFragmentBottomSheet {
            val infoBottomSheet = InfoFragmentBottomSheet()
            val bundle = Bundle()
            bundle.putString(ARG_ID, id)
            infoBottomSheet.arguments = bundle
            return infoBottomSheet
        }
    }
}

