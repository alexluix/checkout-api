package io.maha.checkout.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class DefaultExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler
    fun handleException(exception: Exception): ResponseEntity<Any> {
        log.error("Unmapped exception occurred", exception)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
}
