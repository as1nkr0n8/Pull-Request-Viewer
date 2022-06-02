package com.as1nkr0n8.data.pull_request

import com.as1nkr0n8.domain.common.ErrorCodes
import com.as1nkr0n8.domain.common.InternalError
import com.as1nkr0n8.domain.common.Result
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
class PullRequestRepositoryImplTest {

    private lateinit var repositoryImpl: PullRequestRepositoryImpl
    private val remoteGitRepoDataSource: RemoteGitRepoDataSource = mock()

    @Before
    fun setUp() {
        repositoryImpl = PullRequestRepositoryImpl(remoteGitRepoDataSource)
    }

    @Test
    fun `fetchPullRequestsByState success`() {
        val state = PullRequestState.CLOSED
        val resultList = listOf(
            PullRequestResponse(
                1,
                "title",
                "desc",
                PRState.CLOSED,
                Date(),
                Date(),
                UserInfo("name", "url")
            )
        )
        runTest {
            whenever(remoteGitRepoDataSource.getPullRequestsByState(eq(state))).thenReturn(
                Result.Success(
                    resultList
                )
            )
            when (val result = repositoryImpl.fetchPullRequestsByState(state)) {
                is Result.Success -> {
                    assert(result.data.isNotEmpty())
                }
                is Result.Error -> fail()
            }
        }
    }

    @Test
    fun `fetchPullRequestsByState error`() {
        val state = PullRequestState.CLOSED
        val errorData = InternalError(ErrorCodes.REQUEST_VALIDATION_ERROR)
        runTest {
            whenever(remoteGitRepoDataSource.getPullRequestsByState(eq(state)))
                .thenReturn(Result.Error(errorData))
            when (val result = repositoryImpl.fetchPullRequestsByState(state)) {
                is Result.Success -> {
                    fail()
                }
                is Result.Error -> {
                    assert(result.errorData.hasError())
                    assert(result.errorData.code == ErrorCodes.REQUEST_VALIDATION_ERROR)
                }
            }
        }
    }
}