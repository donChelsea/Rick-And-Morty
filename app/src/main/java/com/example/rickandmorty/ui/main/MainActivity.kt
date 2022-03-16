package com.example.rickandmorty.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.*
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.ui.favorites.FavoritesActivity
import com.example.rickandmorty.ui.info.InfoFragmentBottomSheet

class MainActivity : AppCompatActivity(), Contract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter(this, CharacterModel())

        lifecycleScope.launchWhenResumed {
            presenter.getCharacters()
        }
    }

    override fun setData(
        charactersUpdate: List<CharactersQuery.Result?>?,
        characterUpdate: CharacterQuery.Character?,
        savedData: MutableMap<String, *>?
    ) {
        binding.apply {
            charactersUpdate?.let {
                recyclerview.adapter =
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

    override fun showError(error: String) {
        showProgress()
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
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