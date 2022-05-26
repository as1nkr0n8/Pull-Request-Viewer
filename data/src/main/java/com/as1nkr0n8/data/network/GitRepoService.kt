package com.as1nkr0n8.data.network

import com.as1nkr0n8.data.pull_request.PullRequestResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitRepoService {

    @Headers("Authorization: token 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")
    @GET("repos/as1nkr0n8/Pull-Request-Viewer/pulls")
    suspend fun getPullRequestsByState(@Query("state") state: String): Response<List<PullRequestResponse>>

    @Headers("Authorization: token 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")
    @GET("repos/as1nkr0n8/Pull-Request-Viewer/pulls/{prNumber}")
    suspend fun getPullRequestsByNumber(@Path("prNumber") prNumber: Int): Response<PullRequestResponse?>
}