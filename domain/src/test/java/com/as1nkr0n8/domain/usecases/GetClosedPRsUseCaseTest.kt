package com.as1nkr0n8.domain.usecases

import com.as1nkr0n8.domain.common.ErrorCodes
import com.as1nkr0n8.domain.common.InternalError
import com.as1nkr0n8.domain.common.Result
import com.as1nkr0n8.domain.pull_request.PullRequestModel
import com.as1nkr0n8.domain.pull_request.PullRequestRepository
import com.as1nkr0n8.domain.pull_request.PullRequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetClosedPRsUseCaseTest {
    private val repository: PullRequestRepository = mock()

    private lateinit var useCase: GetClosedPRsUseCase

    @Before
    fun setUp() {
        useCase =
            GetClosedPRsUseCase(repository)
    }

    @Test
    fun `execute success with result`() {
        runTest {
            whenever(repository.fetchPullRequestsByState(eq(PullRequestState.CLOSED))).thenReturn(
                Result.Success(
                    listOf(
                        PullRequestModel(
                            prNumber = 1,
                            title = "Title",
                            description = "",
                            state = PullRequestState.CLOSED,
                            createdDate = Date(),
                            closedDate = Date(),
                            userName = "username",
                            userImageUrl = ""
                        )
                    )
                )
            )
            when (val result = useCase.execute()) {
                is Result.Success -> {
                    assert(result.data.isNotEmpty())
                }
                is Result.Error -> {
                    fail()
                }
            }
        }
    }

    @Test
    fun `execute success with empty result`() {
        runTest {
            whenever(repository.fetchPullRequestsByState(eq(PullRequestState.CLOSED)))
                .thenReturn(Result.Success(emptyList()))
            when (val result = useCase.execute()) {
                is Result.Success -> {
                    assert(result.data.isEmpty())
                }
                is Result.Error -> {
                    fail()
                }
            }
        }
    }

    @Test
    fun `execute error`() {
        runTest {
            whenever(repository.fetchPullRequestsByState(eq(PullRequestState.CLOSED)))
                .thenReturn(
                    Result.Error(
                        InternalError(
                            ErrorCodes.NETWORK_ERROR,
                            "Got network error"
                        )
                    )
                )
            when (val result = useCase.execute()) {
                is Result.Success -> {
                    fail()
                }
                is Result.Error -> {
                    assert(result.errorData.hasError())
                    assert(result.errorData.code == ErrorCodes.NETWORK_ERROR)
                }
            }
        }
    }
}