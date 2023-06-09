package com.example.aplikasigithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: DetailActivity):FragmentStateAdapter(activity) {
    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = FragmentFollowersFollowing()
        fragment.arguments = Bundle().apply {
            putInt(FragmentFollowersFollowing.ARG_POSITION, position + 1)
            putString(FragmentFollowersFollowing.ARG_USERNAME,username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }


}