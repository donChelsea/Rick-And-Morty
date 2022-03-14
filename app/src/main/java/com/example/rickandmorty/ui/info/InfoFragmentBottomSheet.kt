package com.example.rickandmorty.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.Contract
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.InfoBottomSheetDialogBinding
import com.example.rickandmorty.presenter.InfoPresenter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class InfoFragmentBottomSheet : BottomSheetDialogFragment(), Contract.View {
    private lateinit var binding: InfoBottomSheetDialogBinding
    private val presenter = InfoPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InfoBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString(ARG_ID).toString()

        lifecycleScope.launchWhenStarted {
            presenter.fetchCharacter(id)
        }

        presenter.character.observe(viewLifecycleOwner) { char ->
            binding.apply {
                char?.let {
                    name.text = it.name
                    species.text = String.format(getString(R.string.species), it.species)
                    origin.text = String.format(getString(R.string.origin), it.origin?.name)
                    location.text = String.format(getString(R.string.location), it.location?.name)
                    Picasso.get().load(it.image).into(image)
                    hideProgress()
                }
            }
        }
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

    companion object {
        const val TAG = "InfoFragmentBottomSheet"
        const val ARG_ID = "id"

        fun newInstance(id: String?): InfoFragmentBottomSheet {
            val infoBottomSheet = InfoFragmentBottomSheet()
            val bundle = Bundle()
            bundle.putString(ARG_ID, id)
            infoBottomSheet.arguments = bundle
            return infoBottomSheet
        }
    }
}

