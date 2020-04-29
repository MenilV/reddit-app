package com.menilv.reddit.model.base
data class SuccessResponse<T>(val data: T, val success: Boolean, val request_id: String)
