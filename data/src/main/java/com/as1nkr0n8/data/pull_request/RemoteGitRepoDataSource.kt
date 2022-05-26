package com.as1nkr0n8.data.pull_request

import com.as1nkr0n8.data.network.GitRepoService
import com.as1nkr0n8.domain.common.ErrorCodes
import com.as1nkr0n8.domain.common.InternalError
import com.as1nkr0n8.domain.common.Result
import com.as1nkr0n8.domain.pull_request.PullRequestState

class RemoteGitRepoDataSource(private val gitRepoService: GitRepoService) {
    suspend fun getPullRequestsByState(state: PullRequestState): Result<List<PullRequestResponse>, InternalError> {
        return try {
            val response = gitRepoService.getPullRequestsByState(state.toString().lowercase())
            when (response.isSuccessful) {
                true -> {
                    Result.Success(response.body()!!)
                }
                false -> {
                    when (response.code()) {
                        304 -> {
                            //Not modified case
                            Result.Error(InternalError(ErrorCodes.NETWORK_ERROR, "not modified"))
                        }
                        422 -> {
                            //validation failed case
                            Result.Error(
                                InternalError(
                                    ErrorCodes.REQUEST_VALIDATION_ERROR,
                                    "validation failed"
                                )
                            )
                        }
                        else -> {
                            Result.Error(
                                InternalError(
                                    ErrorCodes.SERVER_ERROR,
                                    "unknown network error"
                                )
                            )
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(
                InternalError(
                    ErrorCodes.NETWORK_ERROR,
                    "unknown network error",
                    throwable = ex
                )
            )
        }
    }
}