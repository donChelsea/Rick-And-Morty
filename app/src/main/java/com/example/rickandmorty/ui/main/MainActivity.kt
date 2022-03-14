package com.example.rickandmorty.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.presenter.MainPresenter
import com.example.rickandmorty.ui.info.InfoFragmentBottomSheet

class MainActivity : AppCompatActivity(), Contract.View {
    private lateinit var binding: ActivityMainBinding
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenResumed {
            presenter.fetchCharacters()
        }

        presenter.characters.observe(this) { characters ->
            characters?.let {
                binding.recyclerview.adapter =
                    CharactersAdapter(it) { character -> onCharacterClicked(character) }
                hideProgress()
            }
        }

    }

    private fun onCharacterClicked(character: CharactersQuery.Result?) {
        val infoFragment = InfoFragmentBottomSheet.newInstance(character?.id)
        infoFragment.show(
            supportFragmentManager,
            InfoFragmentBottomSheet.TAG
        )
    }

    override fun showProgress() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            recyclerview.visibility = View.INVISIBLE
        }
    }

    override fun hideProgress() {
        binding.apply {
            progressBar.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
        }
    }


}