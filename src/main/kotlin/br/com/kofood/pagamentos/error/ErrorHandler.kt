package br.com.kofood.pagamentos.error

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.IllegalArgumentException
import java.sql.SQLException
import java.time.LocalDateTime

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(SQLException::class)
    fun handlerSQLException(
        ex: SQLException,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(object {
            val timestamp = LocalDateTime.now()
            val message = "Erro ao acessar o banco de dados!"
            val error = ex.message
            val status = HttpStatus.BAD_REQUEST
            val path = request.contextPath
        })
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(object {
            val timestamp = LocalDateTime.now()
            val message = "Erro ao processar a requisição!"
            val error = ex.message
            val status = HttpStatus.BAD_REQUEST
            val path = request.contextPath
        })
    }
}