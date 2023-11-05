package com.eoinfennessy.localissuetracker.utils

fun validateEmail(email: String): Boolean = email != ""
fun validateNewPassword(password: String): Boolean = password.length >= 8
fun validatePassword(password: String): Boolean = password != ""
fun validateIssueName(name: String): Boolean = name != ""
fun validateIssueDescription(description: String): Boolean = description != ""