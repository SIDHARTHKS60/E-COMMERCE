package com.example.ecomerce.project.exceptions

import com.example.ecomerce.project.model.ErrorMessageModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {
//main class for exception
    @ExceptionHandler
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorMessageModel> {

        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler
    fun handleUserServiceException(ex: UserIdException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler
    fun handleUserNotFoundException(ex: UserNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
//    @ExceptionHandler
//    fun handleProductServiceException(ex: ProductIdException): ResponseEntity<ErrorMessageModel> {
//        val errorMessage = ErrorMessageModel(
//            HttpStatus.NOT_FOUND.value(),
//            ex.message
//        )
//        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
//    }
    @ExceptionHandler
    fun handleProductNotFoundException(ex: ProductNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
}