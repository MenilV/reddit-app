package com.menilv.reddit.model.base
data class SuccessResponse<T>(val data: T, val kind: String)
