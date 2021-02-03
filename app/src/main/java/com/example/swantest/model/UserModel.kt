package com.example.swantest.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class UserModel(
    @ColumnInfo(name = "user_login")
    var login: String = "",

    @PrimaryKey
    @SerializedName("id")
    var user_id: Long = 0L,

    @ColumnInfo(name = "user_node_id")
    var node_id: String = "",

    @ColumnInfo(name = "user_avatar_url")
    var avatar_url: String = "",

    @ColumnInfo(name = "user_gravatar_id")
    var gravatar_id: String = "",

    @ColumnInfo(name = "user_url")
    var url: String = "",

    @ColumnInfo(name = "user_html_url")
    var html_url: String = "",

    @ColumnInfo(name = "user_followers_url")
    var followers_url: String = "",

    @ColumnInfo(name = "user_following_url")
    var following_url: String = "",

    @ColumnInfo(name = "user_gists_url")
    var gists_url: String = "",

    @ColumnInfo(name = "user_starred_url")
    var starred_url: String = "",

    @ColumnInfo(name = "user_subscriptions_url")
    var subscriptions_url: String = "",

    @ColumnInfo(name = "user_organizations_url")
    var organizations_url: String = "",

    @ColumnInfo(name = "user_repos_url")
    var repos_url: String = "",

    @ColumnInfo(name = "user_events_url")
    var events_url: String = "",

    @ColumnInfo(name = "user_received_events_url")
    var received_events_url: String = "",

    @ColumnInfo(name = "user_type")
    var type: String = "",

    @ColumnInfo(name = "user_site_admin")
    var site_admin: Boolean = false
) {

}