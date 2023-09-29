package br.com.kofood.pagamentos.amqp

import br.com.kofood.pagamentos.amqp.QueuesProperties.PAYMENT_CONFIRMED
import br.com.kofood.pagamentos.amqp.QueuesProperties.PAYMENT_CREATED
import br.com.kofood.pagamentos.model.Status
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class PaymentsAMQPConfiguration {

    @Bean
    fun buildPaymentsCreatedQueue(): Queue = QueueBuilder.nonDurable(PAYMENT_CREATED).build()

    @Bean
    fun buildPaymentsConfirmedQueue(): Queue = QueueBuilder.nonDurable(PAYMENT_CONFIRMED).build()


    @Bean
    fun buildRabbitAdmin(cf: ConnectionFactory): RabbitAdmin = RabbitAdmin(cf)

    @Bean
    fun startRabbitAdmin(rabbitAdmin: RabbitAdmin): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener<ApplicationReadyEvent> { event -> rabbitAdmin.initialize() }
    }

}