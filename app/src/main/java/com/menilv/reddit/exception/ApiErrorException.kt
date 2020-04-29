package com.menilv.reddit.exception
import com.menilv.reddit.model.base.ErrorMessageEnum

open class ApiErrorException(
    val errorMessage: ErrorMessageEnum,
    throwable: Throwable? = null
) : GenericException(throwable)
