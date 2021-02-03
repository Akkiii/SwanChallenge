package com.example.swantest.listeners

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.swantest.model.PullRequestModel

@Dao
interface PullRequestDao {
    @Query("Select * from PullRequestModel Where state=:state")
    fun getOpenPullRequest(state: String): List<PullRequestModel>

//    @Query("Select * from pullrequest")
//    fun getClosedPullRequest(): List<PullRequestModel>

    @Insert
    fun insertPullRequest(model: ArrayList<PullRequestModel>)
}