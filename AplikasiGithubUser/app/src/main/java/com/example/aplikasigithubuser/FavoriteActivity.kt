package com.example.aplikasigithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.Room.Users
import com.example.aplikasigithubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    lateinit var favoriteViewModel: FavoriteActivityViewModel
    lateinit var adapter: FavoriteActivityAdapter

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            // Akan dipanggil jika request codenya ADD
            when (result.resultCode) {
                DetailActivity.RESULT_DELETE -> {
                    val position = result.data?.getIntExtra(DetailActivity.EXTRA_POSITION,-1)
                    adapter.removeItem(position!!)

                }
                DetailActivity.RESULT_ADD -> {
                    val username = result.data?.getStringExtra(DetailActivity.EXTRA_ADD_username)
                    val url_avatar = result.data?.getStringExtra(DetailActivity.EXTRA_ADD_imgurl)

                    val users = Users(0,username, url_avatar )
                    var exists = false
                    for (i in 0 until adapter.itemCount) {
                        val existingItem = adapter.userList[i]
                        if (existingItem != null && existingItem.username == users.username && existingItem.url_avatar == users.url_avatar) {
                            // item already exists, don't tambah kedalam recycler view
                            exists = true
                            break
                        }
                    }
                    if (!exists) {
                        adapter.addItem(users)
                    }
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.title = "Favorite"

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerviewFavorite.layoutManager = layoutManager

        favoriteViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)).get(FavoriteActivityViewModel::class.java)

        favoriteViewModel.getAllUsers().observe(this,{it ->
            val usersarray = ArrayList(it)
            setRecyclerView(usersarray)
            Toast.makeText(this@FavoriteActivity, "${usersarray.size} RECEIVED", Toast.LENGTH_SHORT).show()
        })


    }

    private fun setRecyclerView( data: ArrayList<Users>) {
        adapter = FavoriteActivityAdapter(data)
        binding.recyclerviewFavorite.adapter = adapter
        adapter.setOnItemClickCallback(object : FavoriteActivityAdapter.OnItemClickCallback{
            override fun onItemClicked(data: String,position: Int?) {
                val intent = Intent(this@FavoriteActivity,DetailActivity::class.java)
                intent.putExtra("username",data)
                intent.putExtra(DetailActivity.EXTRA_POSITION,position)
                resultLauncher.launch(intent)
            }
        })

    }



    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
}