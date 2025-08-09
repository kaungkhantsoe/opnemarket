package co.opntest.emarket.ui

import android.content.Context
import co.opntest.emarket.R
import co.opntest.emarket.domain.exceptions.ApiException
import co.opntest.emarket.extensions.showToast

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is ApiException -> error?.message
        else -> message
    } ?: context.getString(R.string.error_generic)
}

fun Throwable.showToast(context: Context) =
    context.showToast(userReadableMessage(context))
