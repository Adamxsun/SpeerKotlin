//
//  FollowingAdapter.kt
//  SpeerAssessment
//
//  Created by Xiao Sun on 2023-06-14.
//  Start this class at 4:52 pm mst
package com.example.speerkotlin.following

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.speerkotlin.R
import com.example.speerkotlin.follower.Follower
import com.squareup.picasso.Picasso

// FollowingAdapter class is used to populate the RecyclerView with the list of users being followed
class FollowingAdapter(private val onItemClick: (Follower) -> Unit) : ListAdapter<Follower, FollowingAdapter.ViewHolder>(
    DiffCallback()
) {

    // ViewHolder class is used to hold references to the views inside each item of the RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
    }//ViewHolder

    // onCreateViewHolder is called when a new ViewHolder is needed to represent an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.following_item, parent, false)
        return ViewHolder(itemView)
    }//onCreateViewHolder

    // onBindViewHolder is called to display data for a specific position in the RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val following = getItem(position)
        holder.usernameTextView.text = following.login
        Picasso.get().load(following.avatar_url).into(holder.avatarImageView)
        holder.itemView.setOnClickListener {
            onItemClick(following)
        }
    }//onBindViewHolder

    // DiffCallback is used to calculate the difference between two lists
    // and output a list of update operations that converts the first list into the second one
    class DiffCallback : DiffUtil.ItemCallback<Follower>() {
        // Determines if two items are the same
        override fun areItemsTheSame(oldItem: Follower, newItem: Follower): Boolean {
            return oldItem.login == newItem.login
        }

        // Determines if the content of two items are the same
        override fun areContentsTheSame(oldItem: Follower, newItem: Follower): Boolean {
            return oldItem == newItem
        }
    }//DiffCallback
}//FollowingAdapter
