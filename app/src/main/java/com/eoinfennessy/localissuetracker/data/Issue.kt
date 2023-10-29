package com.eoinfennessy.localissuetracker.data

enum class IssueStatus {
    OPEN, IN_PROGRESS, CLOSED,
}
data class Issue(
    val name: String,
    val description: String,
    val status: IssueStatus,
)