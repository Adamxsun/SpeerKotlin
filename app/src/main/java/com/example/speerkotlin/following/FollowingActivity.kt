//
//  FollowingActivity.kt
//  SpeerAssessment
//
//  Created by Xiao Sun on 2023-06-14.
//  Start this class at 4:53 pm mst
package com.example.speerkotlin.following

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.content.Intent
import com.example.speerkotlin.MainActivity
import com.example.speerkotlin.R
import com.example.speerkotlin.UserViewModel
import com.example.speerkotlin.follower.Follower

// FollowingActivity class is an activity that shows the list of users
// that the current user is following
class FollowingActivity : AppCompatActivity() {

    // Declare variables for the RecyclerView, Adapter and ViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FollowingAdapter
    private lateinit var viewModel: UserViewModel

    // onCreate is called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout defined in XML for this activity
        setContentView(R.layout.activity_following)

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.followingRecyclerView)

        // Initialize the Adapter with a click listener for items
        adapter = FollowingAdapter { following ->
            onFollowingItemClick(following)
        }

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Get the username from the intent extra
        val username = intent.getStringExtra("username")

        // If the username is not null, load the following list
        if (username != null) {
            loadFollowing(username)
        }

        // Initialize and set the click listener for the back button
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }//backButton
    }//onCreate

    // Function to load the list of users that the given username is following
    private fun loadFollowing(username: String) {
        // Request the ViewModel to load the following list
        viewModel.loadFollowing(username)

        // Observe changes in the following list and submit it to the adapter
        viewModel.followingLiveData.observe(this, { followingList ->
            followingList?.let {
                adapter.submitList(followingList)
            }
        })
    }//loadFollowing

    // Function to handle the click event on a following item
    private fun onFollowingItemClick(following: Follower) {
        val username = following.login

        // Create an intent to start the MainActivity with the clicked username as an extra
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("username", username)
        }

        // Start the MainActivity
        startActivity(intent)
    }//onFollowingItemClick

}//FollowingActivity