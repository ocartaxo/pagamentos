package br.com.kofood.pagamentos.http

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("pedidos-ms")
interface OrdersClient {

    @RequestMapping("/pedidos/{id}/pago",method = [RequestMethod.PUT])
    fun updateOrder(@PathVariable id: Long)

}