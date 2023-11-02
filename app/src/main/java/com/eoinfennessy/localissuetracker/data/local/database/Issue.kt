package com.eoinfennessy.localissuetracker.data.local.database

import androidx.room.Dao
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
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val status: IssueStatus,
)

@Dao
interface IssueDao {
    @Query("SELECT * FROM issue ORDER BY id DESC LIMIT 10")
    fun getIssues(): Flow<List<Issue>>

    @Insert
    suspend fun insertIssue(issue: Issue)
}