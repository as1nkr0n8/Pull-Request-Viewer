package com.as1nkr0n8.pullrequestviewer.pull_request

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.as1nkr0n8.domain.common.ErrorCodes
import com.as1nkr0n8.domain.common.InternalError
import com.as1nkr0n8.domain.common.Result
import com.as1nkr0n8.domain.pull_request.PullRequestModel
import com.as1nkr0n8.domain.pull_request.PullRequestState
import com.as1nkr0n8.domain.usecases.GetClosedPRsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.*
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class PullRequestViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private lateinit var viewModel: PullRequestViewModel
    private val useCase: GetClosedPRsUseCase = mock()
    private val listObserver: Observer<List<PullRequestModel>> = mock()
    private val errorObserver: Observer<InternalError> = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = PullRequestViewModel(useCase)
        viewModel.list.observeForever(listObserver)
        viewModel.errorEvent.observeForever(errorObserver)
    }

    @Test
    fun `getClosedPRs with success`() {
        val resultList = listOf(
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
        runTest {
            whenever(useCase.execute()).thenReturn(Result.Success(resultList))
            viewModel.getClosedPRs()
            runCurrent()
        }
        verify(listObserver).onChanged(resultList)
        verify(errorObserver, times(0)).onChanged(any())
    }

    @Test
    fun `getClosedPRs with error`() {
        val errorData = InternalError(ErrorCodes.NETWORK_ERROR)
        runTest {
            whenever(useCase.execute()).thenReturn(Result.Error(errorData))
            viewModel.getClosedPRs()
        }
        verify(listObserver, times(0)).onChanged(any())
        verify(errorObserver).onChanged(errorData)
    }

    @After
    fun tearDown() {
        viewModel.list.removeObserver(listObserver)
        viewModel.errorEvent.removeObserver(errorObserver)
        Dispatchers.resetMain()
    }
}