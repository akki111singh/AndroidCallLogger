package com.assignment.inncircles.model

data class CallInfo(
    val duration: String,
    val number: String,
    val date: String,
    val type: String,
    val recordingUri: String = ""
)