package com.eoinfennessy.localissuetracker.data.service

import android.net.Uri
import com.google.android.gms.tasks.Task


interface StorageService {
    suspend fun uploadFile(uri: Uri, directory: String): Task<Uri>
    suspend fun deleteFile(uri: Uri): Task<Void>
}