package com.as1nkr0n8.data.pull_request

import com.as1nkr0n8.domain.pull_request.PullRequestModel
import com.as1nkr0n8.domain.pull_request.PullRequestState
import com.google.gson.annotations.SerializedName

data class PullRequestResponse(
    @SerializedName("number")
    val prNumber: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val description: String?,
    @SerializedName("state")
    val state: PRState,
    @SerializedName("created_at")
    val createdDate: String,
    @SerializedName("closed_at")
    val closedDate: String,
    @SerializedName("user")
    val userInfo: UserInfo
)

enum class PRState {
    @SerializedName("open")
    OPEN,

    @SerializedName("closed")
    CLOSED,
}

data class UserInfo(
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val userImageUrl: String
)

fun PRState.toPullRequestState(): PullRequestState = when (this) {
    PRState.OPEN -> PullRequestState.OPEN
    PRState.CLOSED -> PullRequestState.CLOSED
}

fun PullRequestResponse.toPullRequestModel() = PullRequestModel(
    this.prNumber,
    this.title,
    this.description ?: "",
    this.state.toPullRequestState(),
    this.createdDate,
    this.closedDate,
    this.userInfo.userName,
    this.userInfo.userImageUrl
)
