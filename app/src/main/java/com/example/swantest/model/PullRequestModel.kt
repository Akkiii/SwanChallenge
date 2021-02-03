package com.example.swantest.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@Entity
data class PullRequestModel(
    @PrimaryKey(autoGenerate = true)
    var primaryId: Int = 0,

    @ColumnInfo(name = "url")
    var url: String = "",

    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "node_id")
    var node_id: String = "",

    @ColumnInfo(name = "html_url")
    var html_url: String = "",

    @ColumnInfo(name = "diff_url")
    var diff_url: String = "",

    @ColumnInfo(name = "patch_url")
    var patch_url: String = "",

    @ColumnInfo(name = "issue_url")
    var issue_url: String = "",

    @ColumnInfo(name = "number")
    var number: Int = 0,

    @ColumnInfo(name = "state")
    var state: String = "",

    @ColumnInfo(name = "locked")
    var locked: Boolean = false,

    @ColumnInfo(name = "title")
    var title: String = "",

    @Embedded
    var user: UserModel? = null,

    @ColumnInfo(name = "body")
    var body: String = "",

    @ColumnInfo(name = "created_at")
    var created_at: String = "",

    @ColumnInfo(name = "updated_at")
    var updated_at: String = "",

    @ColumnInfo(name = "closed_at")
    var closed_at: String? = "",

    @ColumnInfo(name = "merged_at")
    var merged_at: String? = "",

    @ColumnInfo(name = "merge_commit_sha")
    var merge_commit_sha: String = ""
) {
    fun checkClosedDateValue(): String {
        if (closed_at.isNullOrEmpty()) {
            return ""
        } else {
            return "Closed Date: $closed_at"
        }
    }
}