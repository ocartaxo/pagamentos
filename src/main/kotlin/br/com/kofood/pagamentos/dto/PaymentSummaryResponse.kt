package br.com.kofood.pagamentos.dto

import br.com.kofood.pagamentos.model.Status
import java.math.BigDecimal

data class PaymentSummaryResponse(
        val id: Long,
        val value: BigDecimal,
        val name: String,
        val status: Status,
        val expirationDate: String
)