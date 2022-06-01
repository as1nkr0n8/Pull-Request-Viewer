package com.as1nkr0n8.data.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {
    companion object {
        private var instance: Retrofit? = null

        @Synchronized
        fun buildInstance(repoUrl: String): Retrofit {
            return instance ?: kotlin.run {
                val gsonInstance = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")//2011-04-10T20:09:31Z
                    .create()
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).build()
                instance = Retrofit.Builder()
                    .baseUrl(repoUrl)
                    .addConverterFactory(GsonConverterFactory.create(gsonInstance))
                    .client(okHttpClient)
                    .build()
                instance!!
            }
        }

        fun getGitRepoService(): GitRepoService {
            return instance!!.create(GitRepoService::class.java) // app onCreate ensures the instance is created, so !!
        }
    }
}