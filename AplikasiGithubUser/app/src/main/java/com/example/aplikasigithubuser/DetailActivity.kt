package com.example.aplikasigithubuser

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.aplikasigithubuser.Room.Users
import com.example.aplikasigithubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var detailViewModel: DetailViewModel


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_POSITION = "extra_position"
        const val EXTRA_ADD_username = "extra_addusername"
        const val EXTRA_ADD_imgurl= "extra_addimgurl"
        const val RESULT_DELETE = 100
        const val RESULT_ADD = 200

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> {
                // mengset text color u/ night mode
                binding.UserFullname.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
            AppCompatDelegate.MODE_NIGHT_NO -> {
                // mengset text color u/ day mode
                binding.UserFullname.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }

        val intent = intent
        val username_data = intent.getStringExtra("username") as String
        val position = intent.getIntExtra(EXTRA_POSITION, 0)



        detailViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)).get(DetailViewModel::class.java)
        detailViewModel.setUsername(username_data)
        checkbutton(username_data)

        detailViewModel.usersdata.observe(this,{it ->
            setData(it)
        })

        detailViewModel.isLoading.observe(this,{it ->
            showLoading(it)
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username_data
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> {
                tabs.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))

            }
        }
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f



        binding.fab.setOnClickListener {

            val data_kirim = detailViewModel.usersdata.value
            val username = data_kirim?.login
            val ava_url = data_kirim?.avatarUrl


                val user = detailViewModel.findifexist(username!!)
                if (user == null) {
                    // user does not exist, insert user
                    val user = Users(0, username,ava_url)
                    detailViewModel.insert(user)
                    Toast.makeText(this@DetailActivity, "${user.username} added", Toast.LENGTH_SHORT).show()
                    binding.fab.setImageResource(R.drawable.ic_favorite)
                    val intent = Intent()
                    intent.putExtra(EXTRA_ADD_username, username)
                    intent.putExtra(EXTRA_ADD_imgurl,ava_url)
                    setResult(RESULT_ADD, intent)
                } else {
                    // user exists, delete user
                    detailViewModel.deleteUsers(username)
                    Toast.makeText(this@DetailActivity, "$user deleted", Toast.LENGTH_SHORT).show()
                    binding.fab.setImageResource(R.drawable.ic_favorite_border)
                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DELETE, intent)
                }


        }





    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun setData(data: DetailResponse){
        binding.UserName.text = data.login
        binding.UserFullname.text = data.name
        val url_pp = data.avatarUrl
        val jml_followers = data.followers
        val jml_followings = data.following
        Glide.with(this)
            .load(url_pp)
            .into(binding.circleImageView)
        binding.textfollowers.text = getString(R.string.jml_followers,jml_followers)
        binding.textfollowing.text = getString(R.string.jml_following,jml_followings)
    }

    fun checkbutton(it: String){
        val user = detailViewModel.findifexist(it)

        if (user == null) {
            binding.fab.setImageResource(R.drawable.ic_favorite_border)
        } else {
            binding.fab.setImageResource(R.drawable.ic_favorite)
        }
    }
}