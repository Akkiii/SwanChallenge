package com.example.swantest.network

import android.content.Intent
import com.example.swantest.BuildConfig
import com.example.swantest.model.PullRequestModel
import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

object RestClient {
    fun retrofitCallBack(): Retrofit {
        val logging = HttpLoggingInterceptor()
// set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                var request = chain.request()
                request = request.newBuilder()
                    .build()
                val response = chain.proceed(request)

                response
            }).addInterceptor(logging).build()


        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .baseUrl(BuildConfig.SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    interface NetworkCall{
        @GET("square/retrofit/pulls")
        fun getPullRequestData(@Query("state") state : String) : Call<ArrayList<PullRequestModel>>
    }
}