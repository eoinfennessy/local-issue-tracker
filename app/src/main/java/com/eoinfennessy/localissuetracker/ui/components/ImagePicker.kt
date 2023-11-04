package com.eoinfennessy.localissuetracker.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddAPhoto
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePicker(onChangeImageUri: (Uri?) -> Unit, modifier: Modifier = Modifier) {
    var imagePreviewUri: Uri? by remember { mutableStateOf(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        imagePreviewUri = uri
        onChangeImageUri(uri)
    }
    
    Card(
        shape = RoundedCornerShape(10),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier,
        onClick = {
            launcher.launch(PickVisualMediaRequest(
                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
            ))
        }
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Text(text = "Choose\nan Image", textAlign = TextAlign.Center, )

            Box(Modifier.fillMaxHeight(0.8f).aspectRatio(1f)) {
                if (imagePreviewUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imagePreviewUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Image preview",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20))
                            .fillMaxSize()
                    )
                } else {
                    Icon(
                        Icons.TwoTone.AddAPhoto,
                        "Add an Image",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 500, showBackground = true)
@Composable
fun ImagePickerPreview() {
    ImagePicker(onChangeImageUri = {}, modifier = Modifier.fillMaxWidth(0.7f).height(100.dp))
}