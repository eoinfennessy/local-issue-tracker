package com.eoinfennessy.localissuetracker.data.local.database

import android.net.Uri
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.Date

enum class IssueStatus {
    OPEN, IN_PROGRESS, CLOSED,
}

@Entity
data class Issue(
    val name: String,
    val description: String,
    val status: IssueStatus,
    val latitude: Double,
    val longitude: Double,
    val dateCreated: Date,
    val imageUri: Uri?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Dao
interface IssueDao {
    @Query("SELECT * FROM issue ORDER BY id DESC")
    fun getIssues(): Flow<List<Issue>>

    @Query("SELECT * FROM issue WHERE id = :id")
    fun getIssue(id: Int): Flow<Issue>

    @Insert
    suspend fun insertIssue(issue: Issue)

    @Delete
    suspend fun deleteIssue(issue: Issue)
}