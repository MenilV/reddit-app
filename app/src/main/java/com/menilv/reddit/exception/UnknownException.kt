package com.menilv.reddit.exception
import com.menilv.reddit.model.base.ErrorMessageEnum

class UnknownException(
    throwable: Throwable
) : ApiErrorException(ErrorMessageEnum.UNKNOWN, throwable)
