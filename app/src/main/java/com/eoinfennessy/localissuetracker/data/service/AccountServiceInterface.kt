package com.eoinfennessy.localissuetracker.data.service

import com.eoinfennessy.localissuetracker.data.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun signOut()
}