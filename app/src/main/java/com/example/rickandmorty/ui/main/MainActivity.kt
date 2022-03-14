package com.example.rickandmorty.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.CharactersQuery
import com.example.rickandmorty.Contract
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.ui.favorites.FavoritesActivity
import com.example.rickandmorty.ui.info.InfoFragmentBottomSheet

class MainActivity : AppCompatActivity(), Contract.View {
    private lateinit var binding: ActivityMainBinding
    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenResumed {
            presenter.getData()
        }

        presenter.characters.observe(this) { characters ->
            binding.apply {
                recyclerview.adapter =
                    CharactersAdapter(characters) { character -> onCharacterClicked(character) }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_favorites -> {
                val intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}