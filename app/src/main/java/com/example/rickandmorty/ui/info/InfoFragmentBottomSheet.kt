package com.example.rickandmorty.ui.info

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.*
import com.example.rickandmorty.databinding.InfoBottomSheetDialogBinding
import com.example.rickandmorty.ui.main.CharactersAdapter
import com.example.rickandmorty.ui.main.MainPresenter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class InfoFragmentBottomSheet : BottomSheetDialogFragment(), Contract.View {
    private lateinit var binding: InfoBottomSheetDialogBinding
    private lateinit var presenter: InfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = InfoPresenter(this, CharacterModel())
        presenter.characterId = arguments?.getString(ARG_ID).toString()
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

        lifecycleScope.launchWhenStarted {
            presenter.getCharacter(presenter.characterId)
        }
    }

    override fun setData(
        charactersUpdate: List<CharactersQuery.Result?>?,
        characterUpdate: CharacterQuery.Character?
    ) {
        binding.apply {
            characterUpdate?.let {
                name.text = it.name
                species.text = String.format(getString(R.string.species), it.species)
                origin.text = String.format(getString(R.string.origin), it.origin?.name)
                location.text = String.format(getString(R.string.location), it.location?.name)
                Picasso.get().load(it.image).into(image)
                hideProgress()
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
        presenter.onDestroy()
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

