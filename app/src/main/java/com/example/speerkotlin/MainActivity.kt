//
//  MainActivity.kt
//  SpeerAssessment
//
//  Created by Xiao Sun on 2023-06-14.
//  Start this class at 2:23 pm mst
package com.example.speerkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.speerkotlin.follower.FollowerActivity
import com.example.speerkotlin.following.FollowingActivity

// MainActivity is the main entry point of the app where user searches GitHub users
class MainActivity : AppCompatActivity() {

    // Declaring variables for UserViewModel, EditText for username input, and SwipeRefreshLayout
    private lateinit var viewModel: UserViewModel
    private lateinit var usernameEditText: EditText
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    // onCreate is called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing views from layout
        usernameEditText = findViewById(R.id.usernameEditText)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val loginTextView = findViewById<TextView>(R.id.loginTextView)
        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val followersButton = findViewById<Button>(R.id.followersButton)
        val followingButton = findViewById<Button>(R.id.followingButton)
        val avatarImageView = findViewById<ImageView>(R.id.avatarImageView)

        // Initializing the ViewModel
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Observing changes in the User data
        viewModel.userLiveData.observe(this, { user ->
            user?.let {
                loginTextView.text = user.login
                nameTextView.text = user.name ?: "Name: nil"
                Picasso.get().load(user.avatar_url).into(avatarImageView)
                followersButton.text = "Followers: ${user.followers}"
                followingButton.text = "Following: ${user.following}"
            }
        })

        // Setting OnClickListener for search button
        searchButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            viewModel.fetchUserInfo(username)
        }

        // Check if the username was passed from another activity
        val username = intent.getStringExtra("username")
        if (username != null) {
            usernameEditText.setText(username)
            viewModel.fetchUserInfo(username)
        }

        // Setting OnClickListener for following button
        followingButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val intent = Intent(this, FollowingActivity::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }

        // Setting OnClickListener for followers button
        followersButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val intent = Intent(this, FollowerActivity::class.java).apply {
                putExtra("username", username)
            }//intent
            startActivity(intent)
        }//followersButton

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        // Set refresh listener
        swipeRefreshLayout.setOnRefreshListener {
            // On pull-to-refresh, reload the user info if username is not empty
            val username = usernameEditText.text.toString()
            if (username.isNotEmpty()) {
                viewModel.fetchUserInfo(username)
            }

            // Hide the refresh indicator
            swipeRefreshLayout.isRefreshing = false
        }//setOnRefreshListener
    }//onCreate
}//MainActivity

