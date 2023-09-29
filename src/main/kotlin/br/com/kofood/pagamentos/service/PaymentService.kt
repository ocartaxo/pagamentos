package br.com.kofood.pagamentos.service

import br.com.kofood.pagamentos.dto.PaymentRequest
import br.com.kofood.pagamentos.dto.PaymentResponse
import br.com.kofood.pagamentos.dto.PaymentUpdate
import br.com.kofood.pagamentos.http.OrdersClient
import br.com.kofood.pagamentos.mapper.PaymentMapper
import br.com.kofood.pagamentos.model.Payment
import br.com.kofood.pagamentos.model.Status
import br.com.kofood.pagamentos.repository.PaymentRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import kotlin.reflect.KFunction1

@Service
class PaymentService(
    private val repository: PaymentRepository,
    private val ordersClient: OrdersClient,
    private val mapper: PaymentMapper
) {

    fun getAll(page: Pageable) = repository.findAll(page).map { p -> mapper.toSummaryDto(p) }

    fun getById(id: Long) = repository.findById(id)
        .orElseThrow { RuntimeException("Pagamento não encontrado!") }
        .let { mapper.toDto(it) }

    fun create(newPayment: PaymentRequest): PaymentResponse {
        val p = mapper.toEntity(newPayment)
        repository.save(p)
        return mapper.toDto(p)
    }

    fun update(id: Long, newInfo: PaymentUpdate): PaymentResponse {
        val p = repository.findById(id).orElseThrow { RuntimeException("Pagamento não encontrado!") }

        p.update(newInfo)

        return mapper.toDto(p)
    }

    fun deleteById(id: Long) = repository.deleteById(id)

    fun paymentConfirm(id: Long) {
        val p = updatePaymentStatus(id, Status.CONFIRMED)

        ordersClient.updateOrder(p.orderId)
    }

    fun authorizePaymentWithPendency(id: Long){
        val p = updatePaymentStatus(id, Status.CONFIRMED_WITHOUT_INTEGRATION)

    }

    private fun updatePaymentStatus(paymentId: Long, newStatus: Status): Payment{
        val p = repository.findById(paymentId)
            .orElseThrow { EntityNotFoundException("Pagamento não encontrado!") }

        p.status = newStatus
        repository.save(p)

        return p
    }

}



