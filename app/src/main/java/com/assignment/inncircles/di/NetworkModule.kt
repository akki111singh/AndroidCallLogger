package com.assignment.inncircles.di

import android.content.Context
import com.assignment.inncircles.network.CallService
import com.assignment.inncircles.network.InternetHelper
import com.assignment.inncircles.network.NetworkCall
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    var networkBaseUrl = "https://example.com"

    @Provides
    fun provideNetWorkService(retrofit: Retrofit): CallService {
        return retrofit.create(CallService::class.java)
    }


    @Singleton
    @Provides
    fun provideNetworkCall(
        internetHelper: InternetHelper
    ): NetworkCall {
        return NetworkCall(internetHelper)
    }

    @Singleton
    @Provides
    fun provideTenorRetrofit(
         okHttpClient: OkHttpClient,
         gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(networkBaseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().setLenient().create())
    }

    @Singleton
    @Provides
    fun provideInternetHelper(@ApplicationContext context: Context) = InternetHelper(context)

}