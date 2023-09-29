package br.com.kofood.pagamentos.dto

import java.math.BigDecimal

data class PaymentUpdate(

        val value: BigDecimal? = null,
        val name: String? = null,
        val code: String? = null,
)
