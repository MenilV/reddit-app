package com.menilv.reddit.exception
import com.menilv.reddit.model.base.ErrorMessageEnum

class SerializationException(
    throwable: Throwable? = null
) : ApiErrorException(ErrorMessageEnum.WRONG_RESPONSE, throwable)
