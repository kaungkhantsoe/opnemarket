package co.opntest.emarket.domain.exceptions

import co.opntest.emarket.domain.models.Error

object NoConnectivityException : RuntimeException()

data class ApiException(
    val error: Error?,
    val httpCode: Int,
    val httpMessage: String?
) : RuntimeException()
