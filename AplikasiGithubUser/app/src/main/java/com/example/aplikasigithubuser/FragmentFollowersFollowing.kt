package com.example.aplikasigithubuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.databinding.FragmentFollowersFollowingBinding

private const val ARG_POSITION = "posisi"

class FragmentFollowersFollowing : Fragment() {

    private var position: Int = 0
    private var username: String? =""
    private lateinit var binding: FragmentFollowersFollowingBinding
    lateinit var followersFollowingViewModel: FollowersFollowingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersFollowingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username2 = username!!
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewdetail.layoutManager = layoutManager

        if (position == 1) {
            followersFollowingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersFollowingViewModel::class.java)
            followersFollowingViewModel.isLoading.observe(viewLifecycleOwner, Observer { it ->
                showLoading(it)
            })
            followersFollowingViewModel.Start_search("followers",username2)
            followersFollowingViewModel.mulai()
            followersFollowingViewModel.listusers.observe(viewLifecycleOwner, Observer { it ->
                setRecyclerView(it)
            })

        } else {
            followersFollowingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersFollowingViewModel::class.java)
            followersFollowingViewModel.isLoading.observe(viewLifecycleOwner, Observer { it ->
                showLoading(it)
            })
            followersFollowingViewModel.Start_search("following",username2)
            followersFollowingViewModel.mulai()
            followersFollowingViewModel.listusers.observe(viewLifecycleOwner, Observer { it ->
                setRecyclerView(it)
            })
        }

        }


    companion object {
        const val ARG_POSITION: String = "posisi"
        const val ARG_USERNAME: String = "username"
    }


    private fun setRecyclerView( data: List<ResponseFollowersFollowingItem>) {
        val adapter = DetailAdapter(data)
        binding.recyclerviewdetail.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }
}