package br.com.kofood.pagamentos.model

import br.com.kofood.pagamentos.dto.PaymentUpdate
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.math.BigDecimal


@Entity
@Table(name = "pagamentos")
data class Payment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long ?= null,

        @NotNull
        @PositiveOrZero
        @Column(name = "valor")
        var value: BigDecimal,

        @NotBlank
        @Size(max=169)
        @Column(name = "nome_usuario")
        var username: String,

        @NotBlank
        @Size(max=19)
        @Column(name = "numero")
        val number: String,

        @NotBlank
        @Size(max=7)
        @Column(name = "expiracao")
        val expiration: String,

        @NotBlank
        @Size(min = 3, max = 3)
        @Column(name = "codigo")
        var code: String,

        @NotNull
        @Enumerated(value = EnumType.STRING)
        var status: Status = Status.CREATED,

        @NotNull
        @Column(name="pedido_id")
        val orderId: Long,

        @NotNull
        @OneToOne(cascade = [CascadeType.PERSIST])
        @Column(name="forma_pagamento_id")
        val paymentMethod: PaymentMethod

) {
        fun update(newInfo: PaymentUpdate) {
                this.value = newInfo.value ?: this.value
                this.username = newInfo.name ?: this.username
                this.code = newInfo.code ?: this.code
        }
}