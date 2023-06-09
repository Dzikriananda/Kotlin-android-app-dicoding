package com.example.aplikasigithubuser

import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.databinding.ActivityMainBinding
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val layoutManager = LinearLayoutManager(this)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )
        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.recyclerview.layoutManager = layoutManager

        mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.listusers.observe(this,{it ->
            setRecyclerView(it)
        })

        mainViewModel.isLoading.observe(this,{it ->
            showLoading(it)
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_bar).actionView as SearchView

        val settings_menu = menu.findItem(R.id.setting_bar)
        val settings_favorite = menu.findItem(R.id.favorite_bar)
        val settings_search = menu.findItem(R.id.search_bar)



        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> {
                // Set text color for night mode
                settings_menu.setIcon(R.drawable.ic_setting_night)
                settings_favorite.setIcon(R.drawable.ic_favorite_night)
                settings_search.setIcon(R.drawable.ic_search_night)
            }
        }


        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.setQuery(query) // melakukan search
                searchView.clearFocus()
                return true
            }


            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_bar -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.setting_bar -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setRecyclerView( data: List<ItemsItem>) {
        val adapter = RecyclerAdapter(data)
        binding.recyclerview.adapter = adapter
        adapter.setOnItemClickCallback(object : RecyclerAdapter.OnItemClickCallback{
            override fun onItemClicked(data: String) {
                val intent = Intent(this@MainActivity,DetailActivity::class.java)
                intent.putExtra("username",data)
                startActivity(intent)
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}