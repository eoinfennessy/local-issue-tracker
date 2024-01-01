package com.eoinfennessy.localissuetracker.data.service.firebase

import android.net.Uri
import com.eoinfennessy.localissuetracker.data.service.AccountService
import com.eoinfennessy.localissuetracker.data.service.StorageService
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class StorageServiceImpl
@Inject
constructor(private val storage: FirebaseStorage, private val auth: AccountService) :
    StorageService {

    override suspend fun uploadImage(uri: Uri): Task<Uri> {
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${uri.lastPathSegment}")
        val uploadTask = imageRef.putFile(uri)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }
        return urlTask
    }
}