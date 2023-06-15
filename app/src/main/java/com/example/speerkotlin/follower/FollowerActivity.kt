//
//  FollowerActivity.kt
//  SpeerAssessment
//
//  Created by Xiao Sun on 2023-06-14.
//  Start this class at 5:25 pm mst
package com.example.speerkotlin.follower

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

// FollowerActivity is an activity class responsible for
// displaying a list of followers of a user
class FollowerActivity : AppCompatActivity() {

    // Declare RecyclerView, Adapter and ViewModel as properties of the FollowerActivity class
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FollowerAdapter
    private lateinit var viewModel: UserViewModel

    // onCreate method is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view of this activity to activity_followers.xml
        setContentView(R.layout.activity_followers)

        // Initialize the RecyclerView by finding it from the layout
        recyclerView = findViewById(R.id.followerRecyclerView)
        // Initialize the adapter for the RecyclerView
        adapter = FollowerAdapter { follower ->
            onFollowerItemClick(follower)
        }

        // Set the layout manager for the RecyclerView to LinearLayoutManager,
        // which positions the items linearly
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Set the adapter for the RecyclerView
        recyclerView.adapter = adapter

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Get the username from the Intent extras
        val username = intent.getStringExtra("username")
        // If the username is not null, load the followers of that user
        if (username != null) {
            loadFollowers(username)
        }

        // Initialize the back button and set a click listener to finish the activity
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

    }//onCreate

    // Method to load followers of the given username using the ViewModel
    private fun loadFollowers(username: String) {
        viewModel.loadFollowers(username)
        viewModel.followersLiveData.observe(this, { followersList ->
            followersList?.let {
                adapter.submitList(followersList)
            }
        })
    }//loadFollowers

    // Method to handle click event on follower item
    private fun onFollowerItemClick(follower: Follower) {
        // Get the username of the clicked follower
        val username = follower.login
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("username", username)
        }
        // Start the MainActivity
        startActivity(intent)
    }//onFollowerItemClick
}//FollowerActivity