package com.as1nkr0n8.domain.pull_request

import com.as1nkr0n8.domain.common.InternalError
import com.as1nkr0n8.domain.common.Result

interface PullRequestRepository {
    suspend fun fetchAllPullRequests(): Result<List<PullRequestModel>, InternalError>
    suspend fun fetchPullRequestByPRNumber(prNumber: String): Result<PullRequestModel?, InternalError>
    suspend fun fetchPullRequestsByState(state: PullRequestState): Result<List<PullRequestModel>, InternalError>
}