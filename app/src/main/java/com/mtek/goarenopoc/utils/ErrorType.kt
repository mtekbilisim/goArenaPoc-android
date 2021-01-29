package com.mtek.goarenopoc.utils

import androidx.annotation.StringRes
import member.kidzjungle.mtek.R

enum class ErrorType(@StringRes val messageId: Int) {
    UNKNOWN_ERROR(R.string.UNKNOWN_ERROR),
    SUCCESS(R.string.SUCCESS),
    CONNECTION_TIME_OUT(R.string.CONNECTION_TIME_OUT),
}