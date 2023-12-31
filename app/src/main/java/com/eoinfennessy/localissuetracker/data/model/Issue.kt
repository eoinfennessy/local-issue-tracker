package com.eoinfennessy.localissuetracker.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Issue(
    @DocumentId val id: String = "",
    val userId: String = "",
    val name: String = "",
    val description: String = "",
    val status: IssueStatus = IssueStatus.OPEN,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val dateCreated: Date = Date(),
    val imageUri: String? = null,
)

enum class IssueStatus {
    OPEN, IN_PROGRESS, CLOSED,
}

val fakeIssues = List(30) { i ->
    Issue(
        "Issue $i",
        "User $i",
        "name $i",
        "Description $i",
        IssueStatus.OPEN,
        i.toDouble(),
        i.toDouble(),
    )
}