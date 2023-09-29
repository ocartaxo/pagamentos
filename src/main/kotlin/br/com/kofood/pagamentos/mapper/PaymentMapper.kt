package br.com.kofood.pagamentos.mapper

import br.com.kofood.pagamentos.dto.PaymentRequest
import br.com.kofood.pagamentos.dto.PaymentResponse
import br.com.kofood.pagamentos.dto.PaymentSummaryResponse
import br.com.kofood.pagamentos.model.Payment
import org.springframework.stereotype.Component

@Component
object PaymentMapper {

    fun toDto(p: Payment) = PaymentResponse(
            id = p.id!!,
            name = p.name,
            number = p.number,
            value = p.value,
            expirationDate = p.expiration,
            code = p.code,
            status = p.status,
            orderId = p.orderId,
            paymentMethodId = p.paymentMethodId
    )

    fun toSummaryDto(p: Payment) = PaymentSummaryResponse(
            id = p.id!!,
            name = p.name,
            value = p.value,
            status = p.status,
            expirationDate = p.expiration

    )

    fun toEntity(p: PaymentRequest) = Payment(
            value = p.value,
            name = p.name,
            number = p.number,
            expiration = p.expirationDate,
            code = p.code,
            orderId = p.orderId,
            paymentMethodId = p.paymentMethodId
    )
}