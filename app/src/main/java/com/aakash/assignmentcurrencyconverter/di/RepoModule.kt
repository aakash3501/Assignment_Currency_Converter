package com.aakash.assignmentcurrencyconverter.di

import com.aakash.assignmentcurrencyconverter.data.repository.database.CurrencyDatabaseRepoImpl
import com.aakash.assignmentcurrencyconverter.data.repository.network.CurrencyNetworkRepoImpl
import com.aakash.assignmentcurrencyconverter.domain.repository.databaseRepository.CurrencyDatabaseRepo
import com.aakash.assignmentcurrencyconverter.domain.repository.networkRepository.CurrencyNetworkRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    @Singleton
    abstract fun bindCurrencyNetworkRepo(currencyNetworkRepoImpl: CurrencyNetworkRepoImpl): CurrencyNetworkRepo

    @Binds
    @Singleton
    abstract fun bindCurrencyDatabaseRepo(currencyDatabaseRepoImpl: CurrencyDatabaseRepoImpl): CurrencyDatabaseRepo
}