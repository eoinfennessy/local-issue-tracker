package com.eoinfennessy.localissuetracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Issue::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun issueDao(): IssueDao
}