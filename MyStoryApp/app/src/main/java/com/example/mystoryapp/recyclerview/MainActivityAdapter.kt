

package com.example.mystoryapp.recyclerview

/*


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystoryapp.R
import com.example.mystoryapp.response.ListStoryItem

class MainActivityAdapter(private val ListStory: List<ListStoryItem>,private val listener:OnItemClickListener) : RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = ListStory[position]
        Glide.with(holder.itemView.context)
            .load(ItemsViewModel.photoUrl)
            .into(holder.imageView)
        holder.textView.text = ItemsViewModel.name
        holder.itemView.setOnClickListener{
            listener.itemclick(position)
        }

    }

    override fun getItemCount(): Int {
        return ListStory.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_item_photo)
        val textView: TextView = itemView.findViewById(R.id.tv_item_name)
    }
}

*/
