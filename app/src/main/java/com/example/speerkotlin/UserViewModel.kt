//
//  UserRepository.kt
//  SpeerAssessment
//
//  Created by Xiao Sun on 2023-06-14.
//  Start this class at 3:01 pm mst
package com.example.speerkotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speerkotlin.follower.Follower
import kotlinx.coroutines.launch

// Defines UserViewModel class which extends ViewModel
class UserViewModel : ViewModel() {

    // A MutableLiveData to hold the user information
    val userLiveData = MutableLiveData<User>()

    // A MutableLiveData to hold the list of users the current user is following
    val followingLiveData = MutableLiveData<List<Follower>>()

    // A MutableLiveData to hold the list of users who are following the current user
    val followersLiveData = MutableLiveData<List<Follower>>()

    // Function to fetch user information based on username
    fun fetchUserInfo(username: String) {
        // Launching a coroutine within the ViewModel's scope
        viewModelScope.launch {
            // Fetch user info from UserRepository and store it in 'user'
            val user = UserRepository.getUserInfo(username)

            // Post the fetched user data to 'userLiveData' so it can be observed by the UI
            userLiveData.postValue(user)
        }//viewModelScope
    }//fetchUserInfo

    // Function to load the list of users that the current user is following
    fun loadFollowing(username: String) {
        // Launching a coroutine within the ViewModel's scope
        viewModelScope.launch {
            // Fetch the list of following users from UserRepository and store it in 'followingList'
            val followingList = UserRepository.getFollowing(username)

            // Post the fetched following list to 'followingLiveData' so it can be observed by the UI
            followingLiveData.postValue(followingList)
        }//viewModelScope
    }//loadFollowing

    // Function to load the list of users who are following the current user
    fun loadFollowers(username: String) {
        // Launching a coroutine within the ViewModel's scope
        viewModelScope.launch {
            // Fetch the list of followers from UserRepository and store it in 'followersList'
            val followersList = UserRepository.getFollowers(username)

            // Post the fetched followers list to 'followersLiveData' so it can be observed by the UI
            followersLiveData.postValue(followersList)
        }//viewModelScope
    }//loadFollowing
}//UserViewModel
