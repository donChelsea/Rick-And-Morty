package com.example.rickandmorty.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.network.Character
import com.example.rickandmorty.presenter.MainPresenter

class MainActivity : AppCompatActivity(), Contract.View {
    private lateinit var binding: ActivityMainBinding
    private val presenter = MainPresenter(this, Character())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenResumed {
            presenter.fetchCharacters()
        }

        presenter.characters.observe(this) { characters ->
            characters?.let {
                binding.recyclerview.adapter = CharactersAdapter(it)
            }
        }

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun setString(string: String?) {

    }
}