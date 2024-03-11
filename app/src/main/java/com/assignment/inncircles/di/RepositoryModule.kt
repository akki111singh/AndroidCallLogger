package com.assignment.inncircles.di

import com.assignment.inncircles.network.CallRepository
import com.assignment.inncircles.network.CallRepositoryImpl
import com.assignment.inncircles.network.CallService
import com.assignment.inncircles.network.InternetHelper
import com.assignment.inncircles.network.NetworkCall
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @Provides
    fun provideParticipantRepository(
        callService: CallService,
        networkCall: NetworkCall,
    ): CallRepository {
        return CallRepositoryImpl(callService, networkCall)
    }

}
