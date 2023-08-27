package br.com.kofood.pagamentos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class PaymentsApplication

fun main(args: Array<String>) {
    runApplication<PaymentsApplication>(*args)
}
