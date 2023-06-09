package com.example.aplikasigithubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerAdapter(private val mlist: List<ItemsItem>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val Profil_temp = mlist[position]
        val urlImage = Profil_temp.avatarUrl
        val username = Profil_temp.login
        Glide.with(viewHolder.itemView.context)
            .load(urlImage)
            .into(viewHolder.image)

        viewHolder.name.text = username
        viewHolder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(username)
        }

    }
    override fun getItemCount() = mlist.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.profile_name)
        val image: ImageView = view.findViewById(R.id.profile_image)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String)
    }
}