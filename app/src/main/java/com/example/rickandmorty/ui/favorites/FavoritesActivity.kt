package com.example.rickandmorty.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.*
import com.example.rickandmorty.databinding.ActivityFavoritesBinding
import com.example.rickandmorty.ui.main.MainActivity

class FavoritesActivity : AppCompatActivity(), Contract.View {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var presenter: FavoritesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = FavoritesPresenter(this, SharedPreferencesModel(this))

        lifecycleScope.launchWhenStarted {
            presenter.getData()
        }
    }

    override fun setData(
        charactersUpdate: List<CharactersQuery.Result?>?,
        characterUpdate: CharacterQuery.Character?,
        savedData: MutableMap<String, *>?
    ) {
        Log.d("FavoritesActivity", savedData.toString())
    }

    override fun showError(error: String) {
        Log.d("FavoritesActivity", error)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun showProgress() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
//            recyclerview.visibility = View.INVISIBLE
        }
    }

    override fun hideProgress() {
        binding.apply {
            progressBar.visibility = View.GONE
//            recyclerview.visibility = View.VISIBLE
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