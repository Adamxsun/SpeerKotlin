//
//  User.kt
//  SpeerAssessment
//
//  Created by Xiao Sun on 2023-06-14.
//  Start this class at 2:35 pm mst
package com.example.speerkotlin

//model for user type
data class User(val login: String,
                val avatar_url: String,
                val bio: String,
                val name: String,
                val followers: Int,
                val following: Int)
