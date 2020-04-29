package com.menilv.reddit.exception
import com.menilv.reddit.model.base.ErrorMessageEnum

class NoInternetException(
    throwable: Throwable? = null
) : ApiErrorException(ErrorMessageEnum.NO_INTERNET, throwable)
