package com.eoinfennessy.localissuetracker.data.service.firebase.module

import com.eoinfennessy.localissuetracker.data.service.AccountService
import com.eoinfennessy.localissuetracker.data.service.DbService
import com.eoinfennessy.localissuetracker.data.service.StorageService
import com.eoinfennessy.localissuetracker.data.service.firebase.AccountServiceImpl
import com.eoinfennessy.localissuetracker.data.service.firebase.DbServiceImpl
import com.eoinfennessy.localissuetracker.data.service.firebase.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds abstract fun provideDbService(impl: DbServiceImpl): DbService
    @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}