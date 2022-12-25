package com.falcon.unikit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.falcon.unikit.network.Merge

class MyAdapter(val context: Context,private val merges: List<Merge>): Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.merchName.text = merges[position].merchName
        holder.merchPrice.text = merges[position].merchPrice
        Glide.with(context)
            .load(merges[position].source_url)
            .transition(
                DrawableTransitionOptions.withCrossFade(
                    DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                )
            )
            .placeholder(R.drawable.circle)
            .into(holder.mergeImage)
    }

    override fun getItemCount(): Int {
        return merges.size
    }
    class MyViewHolder(itemView: View): ViewHolder(itemView) {
        var merchName: TextView = itemView.findViewById(R.id.merchName)
        var merchPrice: TextView = itemView.findViewById(R.id.merchPrice)
        var mergeImage: ImageView = itemView.findViewById(R.id.merchImage)
    }
}

