package com.eoinfennessy.localissuetracker.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

enum class IssueStatus {
    OPEN, IN_PROGRESS, CLOSED,
}

@Entity
data class Issue(
    val name: String,
    val description: String,
    val status: IssueStatus,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Dao
interface IssueDao {
    @Query("SELECT * FROM issue ORDER BY id DESC")
    fun getIssues(): Flow<List<Issue>>

    @Insert
    suspend fun insertIssue(issue: Issue)

    @Delete
    suspend fun deleteIssue(issue: Issue)
}