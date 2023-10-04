package br.com.kofood.pagamentos.dto

import com.fasterxml.jackson.annotation.JsonCreator
import java.lang.IllegalArgumentException

enum class AcceptablePaymentMethods {
    PIX,
    VOUCHER,
    CREDIT_CARD,
    DEBIT_CARD;

    @JsonCreator
    fun fromString(value: String): AcceptablePaymentMethods {
        for (pm in AcceptablePaymentMethods.values()){
            if (pm.name.equals(value, ignoreCase = true)){
                return pm
            }
        }
        
        throw IllegalArgumentException("Método de pagamento `$value` inválido!")
    }

}