package br.com.kofood.pagamentos.amqp

import br.com.kofood.pagamentos.model.Status

object QueuesProperties {
    val PAYMENT_CREATED = "payment.${Status.CREATED.name.lowercase()}"
    val PAYMENT_CONFIRMED = "payment.${Status.CONFIRMED.name.lowercase()}"

}