package com.assignment.inncircles.network

import com.google.gson.annotations.SerializedName

data class SuccessResponse(

    @SerializedName("success")
    var success: Boolean? = false
)