package com.as1nkr0n8.domain.usecases

import com.as1nkr0n8.domain.common.Result
import com.as1nkr0n8.domain.pull_request.PullRequestModel
import com.as1nkr0n8.domain.pull_request.PullRequestRepository
import com.as1nkr0n8.domain.pull_request.PullRequestState

class GetClosedPRsUseCase(private val pullRequestRepository: PullRequestRepository) {
    companion object {
        const val TAG = "GetClosedPRsUseCase"
    }
    suspend fun execute(): Result<List<PullRequestModel>> {
        //perform any validations

        //get data
        return when(val fetchResult = pullRequestRepository.fetchPullRequestsByState(PullRequestState.CLOSED)) {
            is Result.Success -> {
                println("$TAG: fetch success, result: ${fetchResult.data}")
                fetchResult
            }
            is Result.Error -> {
                println("$TAG: fetch failed, result: ${fetchResult.errorData}")
                fetchResult
            }
        }
    }
}