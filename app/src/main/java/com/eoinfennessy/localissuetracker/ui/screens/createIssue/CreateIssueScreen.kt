package com.eoinfennessy.localissuetracker.ui.screens.createIssue

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.eoinfennessy.localissuetracker.data.local.database.Issue
import com.eoinfennessy.localissuetracker.data.local.database.IssueStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIssueScreen(
    modifier: Modifier = Modifier,
    onSubmitForm: () -> Unit = {},
    createIssueViewModel: CreateIssueViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var nameIsValid by remember { mutableStateOf<Boolean?>(null) }
    var description by remember { mutableStateOf("") }
    var descriptionIsValid by remember { mutableStateOf<Boolean?>(null) }
    val issueStatuses = arrayOf("Open", "In Progress", "Closed")
    var issueStatus by remember { mutableStateOf(issueStatuses[0]) }

    fun validateForm(): Boolean {
        nameIsValid = validateName(name)
        descriptionIsValid = validateDescription(description)
        if (nameIsValid == false) return false
        if (descriptionIsValid == false) return false
        return true
    }

    fun submitForm() {
        val issue = Issue(name, description, issueStatusStringToEnum(issueStatus))
        createIssueViewModel.addIssue(issue)
        onSubmitForm()
    }

    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
        Column {
            AnimatedVisibility(visible = (nameIsValid == false)) {
                Text(
                    text = "Name cannot be empty",
                    style = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.error)
                )
            }
            TextField(
                label = { Text(text = "Name") },
                placeholder = { Text(text = "Name") },
                value = name,
                onValueChange = { name = it; nameIsValid = validateName(it) },
                isError = (nameIsValid == false)
            )
        }

        Column {
            AnimatedVisibility(visible = (descriptionIsValid == false)) {
                Text(
                    text = "Description cannot be empty",
                    style = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.error)
                )
            }
            TextField(
                label = { Text(text = "Description") },
                placeholder = { Text(text = "Description") },
                value = description,
                onValueChange = { description = it; descriptionIsValid = validateDescription(it) },
                isError = (descriptionIsValid == false)
            )
        }


        var expanded by remember { mutableStateOf(false) }
        Box {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = issueStatus,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    issueStatuses.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                issueStatus = item
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        
        Button(onClick = {
            if (validateForm()) {
                submitForm()
            }
        }
        ) {
            Text(text = "Submit")
        }
    }
}

fun validateName(name: String): Boolean = name != ""
fun validateDescription(description: String): Boolean = description != ""

fun issueStatusStringToEnum(issueStatus: String): IssueStatus {
    return when (issueStatus) {
        "Open" -> IssueStatus.OPEN
        "In Progress" -> IssueStatus.IN_PROGRESS
        "Closed" -> IssueStatus.CLOSED
        else -> throw Error("Invalid issue status string provided: $issueStatus")
    }
}
