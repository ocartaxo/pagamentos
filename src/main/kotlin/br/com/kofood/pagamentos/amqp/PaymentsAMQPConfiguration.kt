package br.com.kofood.pagamentos.amqp

import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class PaymentsAMQPConfiguration {

    @Bean
    fun messageConverter() = Jackson2JsonMessageConverter()

    @Bean
    fun fanoutExchange(): FanoutExchange = FanoutExchange(PAYMENT_EXCHANGE)

    @Bean
    fun buildRabbitAdmin(cf: ConnectionFactory): RabbitAdmin = RabbitAdmin(cf)

    @Bean
    fun startRabbitAdmin(rabbitAdmin: RabbitAdmin): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener<ApplicationReadyEvent> { event -> rabbitAdmin.initialize() }
    }

    @Bean
    fun rabbitTemplate(
        cf: ConnectionFactory,
        mc: Jackson2JsonMessageConverter
    ): RabbitTemplate {

        val rabbitTemplate = RabbitTemplate(cf)
        rabbitTemplate.messageConverter = mc

        return rabbitTemplate
    }

    companion object{
        const val PAYMENT_EXCHANGE = "payment.exchange"

    }

}