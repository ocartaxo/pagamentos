package br.com.kofood.pagamentos.service

import br.com.kofood.pagamentos.amqp.PaymentsAMQPConfiguration
import br.com.kofood.pagamentos.dto.PaymentRequest
import br.com.kofood.pagamentos.dto.PaymentResponse
import br.com.kofood.pagamentos.dto.PaymentUpdate
import br.com.kofood.pagamentos.http.OrdersClient
import br.com.kofood.pagamentos.mapper.PaymentMapper
import br.com.kofood.pagamentos.model.Payment
import br.com.kofood.pagamentos.model.PaymentMethod
import br.com.kofood.pagamentos.model.Status
import br.com.kofood.pagamentos.repository.PaymentRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val ordersClient: OrdersClient,
    private val rabbitTemplate: RabbitTemplate,
    private val mapper: PaymentMapper
) {

    fun getAll(page: Pageable) = paymentRepository.findAll(page).map { p -> mapper.toSummaryDto(p) }

    fun getById(id: Long) = paymentRepository.findById(id)
        .orElseThrow { RuntimeException("Pagamento não encontrado!") }
        .let { mapper.toDto(it) }

    fun create(newPayment: PaymentRequest): PaymentResponse {
        val p = mapper.toEntity(newPayment)
        paymentRepository.save(p)

        val dto = mapper.toDto(p)
        rabbitTemplate.convertAndSend(PaymentsAMQPConfiguration.PAYMENT_EXCHANGE, "", dto)

        return dto
    }

    fun update(id: Long, newInfo: PaymentUpdate): PaymentResponse {
        val p = paymentRepository.findById(id).orElseThrow { RuntimeException("Pagamento não encontrado!") }

        p.update(newInfo)

        return mapper.toDto(p)
    }

    fun deleteById(id: Long) = paymentRepository.deleteById(id)

    fun paymentConfirm(id: Long) {
        val dto = mapper.toDto(updatePaymentStatus(id, Status.CONFIRMED))
        rabbitTemplate.convertAndSend(PaymentsAMQPConfiguration.PAYMENT_EXCHANGE, "", dto)
//        ordersClient.approvePayment(dto.id)
    }

    fun cancelPayment(id: Long) {
        val p = updatePaymentStatus(id, Status.CANCELLED)
        ordersClient.cancelPayment(p.orderId)
    }

    fun authorizePaymentWithPendency(id: Long) = updatePaymentStatus(id, Status.CONFIRMED_WITHOUT_INTEGRATION)

    private fun updatePaymentStatus(paymentId: Long, newStatus: Status): Payment{
        val p = paymentRepository.findById(paymentId)
            .orElseThrow { EntityNotFoundException("Pagamento com id `$paymentId` não encontrado!") }

        p.status = newStatus
        paymentRepository.save(p)

        return p
    }

}



