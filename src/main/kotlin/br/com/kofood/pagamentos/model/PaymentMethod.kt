package br.com.kofood.pagamentos.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "forma_pagamento")
data class PaymentMethod(
    @Id
    val id: Long? = null,

    @NotNull
    @Column(name = "nome")
    val name: String
)
