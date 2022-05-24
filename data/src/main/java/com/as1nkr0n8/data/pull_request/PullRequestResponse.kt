package com.as1nkr0n8.data.pull_request

import com.as1nkr0n8.domain.pull_request.PullRequestModel
import com.as1nkr0n8.domain.pull_request.PullRequestState
import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class PullRequestResponse(
    @SerializedName("number")
    val prNumber: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val description: String,
    @SerializedName("state")
    val state: PullRequestState,
    @SerializedName("created_at")
    val createdDate: OffsetDateTime,
    @SerializedName("closed_at")
    val closedDate: OffsetDateTime,
    @SerializedName("user")
    val userInfo: UserInfo
)

data class UserInfo(
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val userImageUrl: String
)

fun PullRequestResponse.toPullRequestModel() = PullRequestModel(
    this.prNumber,
    this.title,
    this.description,
    this.state,
    this.createdDate,
    this.closedDate,
    this.userInfo.userName,
    this.userInfo.userImageUrl
)
