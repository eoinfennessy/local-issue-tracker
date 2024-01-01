package com.eoinfennessy.localissuetracker.ui.screens.createIssue

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eoinfennessy.localissuetracker.data.model.Issue
import com.eoinfennessy.localissuetracker.data.service.DbService
import com.eoinfennessy.localissuetracker.data.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber.Forest.e
import javax.inject.Inject

@HiltViewModel
class CreateIssueViewModel @Inject constructor(
    private val dbService: DbService,
    private val storageService: StorageService
) : ViewModel() {
    fun addIssue(issue: Issue) {
        viewModelScope.launch {
            if (!issue.imageUri.isNullOrEmpty()) {
                val urlTask = storageService.uploadFile(Uri.parse(issue.imageUri), "images")
                val url = urlTask.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        // TODO: Display snackbar showing error
                        e("task not successful: ${task.exception}")
                    }
                }.await()
                dbService.save(issue.copy(imageUri = url.toString()))
            } else {
                dbService.save(issue)
            }
        }
    }
}