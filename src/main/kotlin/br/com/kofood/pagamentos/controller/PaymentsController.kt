package br.com.kofood.pagamentos.controller

import br.com.kofood.pagamentos.amqp.PaymentsAMQPConfiguration.Companion.PAYMENT_EXCHANGE
import br.com.kofood.pagamentos.dto.PaymentRequest
import br.com.kofood.pagamentos.dto.PaymentResponse
import br.com.kofood.pagamentos.dto.PaymentUpdate
import br.com.kofood.pagamentos.service.PaymentService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import jakarta.validation.Valid
import org.jetbrains.annotations.NotNull
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/pagamentos")
class PaymentsController(
    private val service: PaymentService

) {

    @PostMapping
    fun create(
        @RequestBody @Valid request: PaymentRequest,
        builder: UriComponentsBuilder
    ): ResponseEntity<PaymentResponse> {
        val response = service.create(request)
        val uri = builder.path("/pagamentos/{id}").buildAndExpand(response.id).toUri()

        return ResponseEntity.created(uri).body(response)
    }

    @GetMapping
    fun list(@PageableDefault(size = 10) page: Pageable) = ResponseEntity.ok(service.getAll(page))

    @GetMapping("/{id}")
    fun show(@PathVariable @NotNull id: Long) = ResponseEntity.ok(service.getById(id))

    @RequestMapping("/{id}", method = [RequestMethod.PUT, RequestMethod.PATCH])
    fun update(
        @PathVariable id: Long,
        @RequestBody newInfo: PaymentUpdate
    ) = ResponseEntity.ok(service.update(id, newInfo))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.deleteById(id))

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "updatePayment", fallbackMethod = "authorizedPaymentWithPendency")
    fun confirmPayment(@PathVariable id: Long) = ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.paymentConfirm(id))

    @PatchMapping("/{id}/cancelar")
    fun cancelPayment(@PathVariable id: Long) = ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.cancelPayment(id))

    fun authorizedPaymentWithPendency(id: Long, e: Exception) = service.authorizePaymentWithPendency(id)
}