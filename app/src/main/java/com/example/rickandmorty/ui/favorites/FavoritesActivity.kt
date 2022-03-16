package com.example.rickandmorty.ui.favorites

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rickandmorty.*
import com.example.rickandmorty.databinding.ActivityFavoritesBinding
import kotlinx.coroutines.launch

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
        savedData: List<Pair<String, Any?>>?
    ) {
        binding.apply {
            savedData?.let { favorites ->
                recyclerview.adapter = FavoritesAdapter(favorites) {pair -> onFavoriteClicked(pair)}
            }
        }
    }

    private fun onFavoriteClicked(pair: Pair<String, Any?>) {
        remove(pair)
    }

    private fun remove(pair: Pair<String, Any?>) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Remove ${pair.second}?")
            .setCancelable(false)
            .setPositiveButton("Proceed") { _, _ ->
                lifecycleScope.launch { presenter.remove(pair.first) }
                finish()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Delete")
        alert.show()
    }

    override fun showError(error: String) {
        showProgress()
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}