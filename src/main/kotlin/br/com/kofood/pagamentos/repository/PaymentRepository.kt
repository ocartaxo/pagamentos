package br.com.kofood.pagamentos.repository

import br.com.kofood.pagamentos.model.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentRepository : JpaRepository<Payment, Long>{
}