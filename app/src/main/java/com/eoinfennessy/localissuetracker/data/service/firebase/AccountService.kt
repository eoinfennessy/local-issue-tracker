package com.eoinfennessy.localissuetracker.data.service.firebase

import com.eoinfennessy.localissuetracker.data.model.User
import com.eoinfennessy.localissuetracker.data.service.AccountService
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val authStateListener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous, it.email) } ?: User())
                }
            val idTokenListener =
                FirebaseAuth.IdTokenListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous, it.email) } ?: User())
                }
            auth.addAuthStateListener(authStateListener)
            auth.addIdTokenListener(idTokenListener)
            awaitClose {
                auth.removeAuthStateListener(authStateListener)
                auth.removeIdTokenListener(idTokenListener)
            }
        }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun linkAccount(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser!!.linkWithCredential(credential).await()
    }

    override suspend fun deleteAccount() {
        auth.currentUser?.delete()?.await()
    }

    override suspend fun signOut() {
        if (auth.currentUser?.isAnonymous == true) {
            auth.currentUser?.delete()
        }
        auth.signOut()

        // Sign in the user anonymously after sign-out
        createAnonymousAccount()
    }
}
