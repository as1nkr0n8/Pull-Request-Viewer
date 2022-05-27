package com.as1nkr0n8.data.pull_request

import com.as1nkr0n8.data.network.GitRepoService
import com.as1nkr0n8.domain.common.ErrorCodes
import com.as1nkr0n8.domain.common.Result
import com.as1nkr0n8.domain.pull_request.PullRequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteGitRepoDataSourceTest {

    private lateinit var remoteGitRepoDataSource: RemoteGitRepoDataSource
    private val gitRepoService: GitRepoService = mock()

    @Before
    fun setUp() {
        remoteGitRepoDataSource = RemoteGitRepoDataSource(gitRepoService)
    }

    @Test
    fun `getPullRequestsByState with closed state success`() {
        val state = PullRequestState.CLOSED
        runTest {
            whenever(gitRepoService.getPullRequestsByState(eq(state.toString().lowercase())))
                .thenReturn(
                    Response.success(
                        listOf(
                            PullRequestResponse(
                                1,
                                "title",
                                "desc",
                                PRState.CLOSED,
                                "",
                                "",
                                UserInfo("name", "url")
                            )
                        )
                    )
                )
            when (val result = remoteGitRepoDataSource.getPullRequestsByState(state)) {
                is Result.Success -> {
                    assert(result.data.isNotEmpty())
                }
                is Result.Error -> fail()
            }
        }
    }

    @Test
    fun `getPullRequestsByState with closed state and validation error`() {
        val state = PullRequestState.CLOSED
        runTest {
            whenever(gitRepoService.getPullRequestsByState(eq(state.toString().lowercase())))
                .thenReturn(Response.error(422, "".toResponseBody()))
            when (val result = remoteGitRepoDataSource.getPullRequestsByState(state)) {
                is Result.Success -> fail()
                is Result.Error -> {
                    assert(result.errorData.hasError())
                    assert(result.errorData.code == ErrorCodes.REQUEST_VALIDATION_ERROR)
                }
            }
        }
    }

    @Test
    fun `getPullRequestsByState with closed state and exception`() {
        val state = PullRequestState.CLOSED
        runTest {
            whenever(gitRepoService.getPullRequestsByState(eq(state.toString().lowercase())))
                .then { throw IOException() }
            when (val result = remoteGitRepoDataSource.getPullRequestsByState(state)) {
                is Result.Success -> fail()
                is Result.Error -> {
                    assert(result.errorData.hasError())
                    assert(result.errorData.code == ErrorCodes.NETWORK_ERROR)
                    assert(result.errorData.throwable != null)
                }
            }
        }
    }
}