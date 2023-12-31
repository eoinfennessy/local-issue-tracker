package com.eoinfennessy.localissuetracker.data.service.firebase

import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.data.service.AccountService
import com.eoinfennessy.localissuetracker.data.service.DbService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DbServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
    DbService {

    override val issues: Flow<List<Issue>>
        get() = firestore.collection(ISSUE_COLLECTION).dataObjects()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userIssues: Flow<List<Issue>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore.collection(ISSUE_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id).dataObjects()
            }

    override suspend fun getIssue(issueId: String): Issue? {
        return firestore.collection(ISSUE_COLLECTION).document(issueId).get().await().toObject()
    }

    override suspend fun save(issue: Issue): String {
        val issueWithUserId = issue.copy(userId = auth.currentUserId)
        return firestore.collection(ISSUE_COLLECTION).add(issueWithUserId).await().id
    }

    override suspend fun update(issue: Issue) {
        firestore.collection(ISSUE_COLLECTION).document(issue.id).set(issue).await()
    }

    override suspend fun delete(issueId: String) {
        firestore.collection(ISSUE_COLLECTION).document(issueId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val ISSUE_COLLECTION = "issues"
    }
}