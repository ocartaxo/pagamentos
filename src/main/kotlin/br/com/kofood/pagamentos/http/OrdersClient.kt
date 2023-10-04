package br.com.kofood.pagamentos.http

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("pedidos-ms", path = "/pedidos")
interface OrdersClient {

    @RequestMapping("/{id}/pagar", method = [RequestMethod.PUT])
    fun approvePayment(@PathVariable id: Long)

    @RequestMapping("/{id}/cancelar", method = [RequestMethod.PUT])
    fun cancelPayment(@PathVariable id: Long)
}