package com.as1nkr0n8.pullrequestviewer.pull_request

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.as1nkr0n8.data.network.RetrofitClient
import com.as1nkr0n8.data.pull_request.PullRequestRepositoryImpl
import com.as1nkr0n8.data.pull_request.RemoteGitRepoDataSource
import com.as1nkr0n8.domain.common.InternalError
import com.as1nkr0n8.domain.common.Result
import com.as1nkr0n8.domain.pull_request.PullRequestModel
import com.as1nkr0n8.domain.usecases.GetClosedPRsUseCase
import kotlinx.coroutines.launch

class PullRequestViewModel(val useCase: GetClosedPRsUseCase) : ViewModel() {
    val list = MutableLiveData<List<PullRequestModel>>()
    val errorEvent = MutableLiveData<InternalError>()

    fun getClosedPRs() {
        viewModelScope.launch {
            when (val result = useCase.execute()) {
                is Result.Success -> {
                    list.postValue(result.data)
                }
                is Result.Error -> errorEvent.postValue(result.errorData)
            }
        }
    }
}

class PullRequestViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PullRequestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            //TODO: Rework the creation
            return PullRequestViewModel(
                useCase = GetClosedPRsUseCase(
                    PullRequestRepositoryImpl(
                        RemoteGitRepoDataSource(
                            RetrofitClient.getGitRepoService()
                        )
                    )
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}