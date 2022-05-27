package com.as1nkr0n8.data.pull_request

import com.as1nkr0n8.domain.common.InternalError
import com.as1nkr0n8.domain.common.Result
import com.as1nkr0n8.domain.pull_request.PullRequestModel
import com.as1nkr0n8.domain.pull_request.PullRequestRepository
import com.as1nkr0n8.domain.pull_request.PullRequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PullRequestRepositoryImpl(private val remoteGitRepoDataSource: RemoteGitRepoDataSource) :
    PullRequestRepository {
    override suspend fun fetchAllPullRequests(): Result<List<PullRequestModel>, InternalError> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPullRequestByPRNumber(prNumber: String): Result<PullRequestModel?, InternalError> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPullRequestsByState(state: PullRequestState): Result<List<PullRequestModel>, InternalError> {
        return when (val result =
            withContext(Dispatchers.IO) { remoteGitRepoDataSource.getPullRequestsByState(state) }) {
            is Result.Success -> {
                Result.Success(result.data.map { it.toPullRequestModel() })
            }
            is Result.Error -> result
        }
    }
}