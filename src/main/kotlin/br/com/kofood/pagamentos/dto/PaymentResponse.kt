package br.com.kofood.pagamentos.dto

import br.com.kofood.pagamentos.model.Status
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal

data class PaymentResponse(
        val id: Long,
        @JsonProperty("valor")
        val value: BigDecimal,
        @JsonProperty("nome")
        val name: String,
        @JsonProperty("numero")
        val number: String,
        @JsonProperty("data_expiracao")
        val expirationDate: String,
        @JsonProperty("codigo")
        val code: String,
        @JsonProperty("pedido_id")
        val orderId: Long,
        @JsonProperty("metodo_pagamento_id")
        val paymentMethodId: Long
)
