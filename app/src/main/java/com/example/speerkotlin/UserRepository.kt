//
//  UserRepository.kt
//  SpeerAssessment
//
//  Created by Xiao Sun on 2023-06-14.
//  Start this class at 2:35 pm mst
package com.example.speerkotlin

import com.example.speerkotlin.follower.Follower
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// Define UserRepository as an object (singleton)
object UserRepository {

    // Declare a private variable 'api' of type GitHubApi
    private val api: GitHubApi

    // Initialization block for the UserRepository
    init {
        // Create an instance of Retrofit with a base URL and Gson converter factory
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Initialize 'api' by creating an implementation of the GitHubApi interface with Retrofit
        api = retrofit.create(GitHubApi::class.java)
    }

    // Function to fetch user information based on the given username
    // This is a suspend function which means it can be paused and resumed
    suspend fun getUserInfo(username: String): User? {
        return api.getUserInfo(username)
    }

    // Function to fetch a list of followers for a given username
    // This is a suspend function which means it can be paused and resumed
    suspend fun getFollowers(username: String): List<Follower>? {
        return api.getFollowers(username)
    }

    // Function to fetch a list of users that the given username is following
    // This is a suspend function which means it can be paused and resumed
    suspend fun getFollowing(username: String): List<Follower>? {
        return api.getFollowing(username)
    }

    // Define an interface 'GitHubApi' which declares the endpoints to be used with Retrofit
    interface GitHubApi {
        // Declare an endpoint for getting user information
        @GET("users/{username}")
        suspend fun getUserInfo(@Path("username") username: String): User?

        // Declare an endpoint for getting the list of followers of a user
        @GET("users/{username}/followers")
        suspend fun getFollowers(@Path("username") username: String): List<Follower>?

        // Declare an endpoint for getting the list of users a given user is following
        @GET("users/{username}/following")
        suspend fun getFollowing(@Path("username") username: String): List<Follower>?
    } //GitHubApi
}//UserRepository