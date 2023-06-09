package com.example.aplikasigithubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasigithubuser.Room.Users

class FavoriteActivityAdapter(val userList: ArrayList<Users>) :
    RecyclerView.Adapter<FavoriteActivityAdapter.UserViewHolder>() {
    private lateinit var onItemClickCallback: FavoriteActivityAdapter.OnItemClickCallback

    fun setOnItemClickCallback (onItemClickCallback: FavoriteActivityAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_row ,parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
        val Profil_temp = userList[position]
        val urlImage = Profil_temp.url_avatar
        val username = Profil_temp.username!!
        Glide.with(viewHolder.itemView.context)
            .load(urlImage)
            .into(viewHolder.image)

        viewHolder.name.text = username

        viewHolder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(username, position)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.profile_name)
        val image: ImageView = view.findViewById(R.id.profile_image)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: String,position: Int?)
    }

    fun removeItem(position: Int) {
        this.userList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.userList.size)
    }

    fun addItem(user: Users) {
        this.userList.add(user)
        notifyItemInserted(this.userList.size - 1)
    }
}