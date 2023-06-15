//
//  FollowerAdapter.kt
//  SpeerAssessment
//
//  Created by Xiao Sun on 2023-06-14.
//  Start this class at 5:24 pm mst
package com.example.speerkotlin.follower

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.speerkotlin.R
import com.squareup.picasso.Picasso

// FollowerAdapter class is used to populate the RecyclerView
// with the list of users that are following the current user
class FollowerAdapter(private val onItemClick: (Follower) -> Unit) : ListAdapter<Follower, FollowerAdapter.ViewHolder>(
    DiffCallback()
) {

    // ViewHolder class is used to hold references to the views inside each item of the RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Reference to the ImageView to display the follower's avatar
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        // Reference to the TextView to display the follower's username
        val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
    }//ViewHolder

    // onCreateViewHolder is called when a new ViewHolder is needed to represent an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout for a follower item and create a ViewHolder to hold its views
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.follower_item, parent, false)
        return ViewHolder(itemView)
    }//onCreateViewHolder

    // onBindViewHolder is called to display data for a specific position in the RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the follower data for this position
        val follower = getItem(position)
        // Set the username in the TextView
        holder.usernameTextView.text = follower.login
        // Load the follower's avatar into the ImageView using Picasso
        Picasso.get().load(follower.avatar_url).into(holder.avatarImageView)
        // Set a click listener for this item that triggers the passed in 'onItemClick' function
        holder.itemView.setOnClickListener {
            onItemClick(follower)
        }//setOnClickListener
    }//onBindViewHolder

    // DiffCallback is used to calculate the difference between two lists and output a list of update operations
    // that converts the first list into the second one
    class DiffCallback : DiffUtil.ItemCallback<Follower>() {
        // Determines if two items are the same
        override fun areItemsTheSame(oldItem: Follower, newItem: Follower): Boolean {
            return oldItem.login == newItem.login
        }//areItemsTheSame

        // Determines if the content of two items are the same
        override fun areContentsTheSame(oldItem: Follower, newItem: Follower): Boolean {
            return oldItem == newItem
        }//areContentsTheSame
    }//DiffCallback

}//FollowerAdapter
