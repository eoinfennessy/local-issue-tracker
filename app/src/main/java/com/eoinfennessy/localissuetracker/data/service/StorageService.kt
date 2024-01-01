package com.eoinfennessy.localissuetracker.data.service

import android.net.Uri
import com.google.android.gms.tasks.Task


interface StorageService {
    suspend fun uploadImage(uri: Uri): Task<Uri>
}