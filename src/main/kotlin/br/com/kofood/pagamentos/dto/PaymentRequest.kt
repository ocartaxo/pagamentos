package br.com.kofood.pagamentos.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal


data class PaymentRequest(
        @NotBlank
        @JsonProperty("valor")
        val value: BigDecimal,
        @NotBlank
        @JsonProperty("nome")
        val name: String,
        @NotBlank
        @JsonProperty("numero")
        val number: String,
        @NotBlank
        @JsonProperty("data_expiracao")
        val expirationDate: String,
        @NotBlank
        @JsonProperty("codigo")
        val code: String,
        @NotNull
        @JsonProperty("pedido_id")
        val orderId: Long,
        @NotNull
        @JsonProperty("metodo_pagamento_id")
        val paymentMethodId: Long

)
