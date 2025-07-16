package com.aakash.assignmentcurrencyconverter.di

import com.aakash.assignmentcurrencyconverter.domain.usecase.FetchCurrencyUseCase
import com.aakash.assignmentcurrencyconverter.domain.usecaseImpl.FetchCurrencyUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindFetchCurrencyUseCase(fetchCurrencyUseCaseImpl: FetchCurrencyUseCaseImpl): FetchCurrencyUseCase
}