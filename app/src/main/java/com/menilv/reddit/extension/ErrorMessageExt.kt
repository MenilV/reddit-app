package com.menilv.reddit.extension

import com.menilv.reddit.R
import com.menilv.reddit.model.base.ErrorMessageEnum
import com.menilv.reddit.model.base.ErrorMessageEnum.*

fun ErrorMessageEnum.getErrorMessageStringRes(): Int {
  return when (this) {
    SOMETHING_WENT_WRONG        -> R.string.something_went_wrong
    NO_INTERNET                 -> R.string.no_internet_connection
    WRONG_RESPONSE              -> R.string.wrong_response
    UNKNOWN                     -> R.string.unknown_issue
    SERVICE_TEMP_UNAVAILABLE    -> R.string.service_is_temporarily_unavailable
  }
} 
