package com.example.rickandmorty.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.network.Character
import com.example.rickandmorty.network.apolloClient
import com.example.rickandmorty.presenter.Presenter

class MainActivity : AppCompatActivity(), Contract.View {
    private lateinit var binding: ActivityMainBinding
    private val presenter = Presenter(this, Character())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenResumed {
            val response = apolloClient.query(CharactersQuery()).execute()

            Log.d("Characters", "Success: ${response.data}")
        }

        binding.button.setOnClickListener { presenter.onButtonClick() }

    }

    override fun showProgress() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.INVISIBLE
        }
    }

    override fun hideProgress() {
        binding.apply {
            progressBar.visibility = View.GONE
            textView.visibility = View.VISIBLE
        }
    }

    override fun setString(string: String?) {
        binding.apply {
            textView.text = string
        }
    }
}