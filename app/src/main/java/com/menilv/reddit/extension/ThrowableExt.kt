package com.menilv.reddit.extension

import com.menilv.reddit.exception.ApiErrorException
import com.menilv.reddit.model.base.ErrorMessageEnum

fun Throwable.toErrorMessage(): ErrorMessageEnum {
  return if (this is ApiErrorException) {
    this.errorMessage
  } else {
    ErrorMessageEnum.SOMETHING_WENT_WRONG
  }
}
