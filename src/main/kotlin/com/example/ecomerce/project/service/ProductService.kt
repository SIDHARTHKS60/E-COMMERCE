package com.example.ecomerce.project.service

//import com.example.ecomerce.project.exceptions.ProductNotFoundException
import com.example.ecomerce.project.model.Product
import com.example.ecomerce.project.repository.ProductRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


@Service
class ProductService
    (val productRepository: ProductRepository) {
        fun getAllProducts(): Flux<Product> {
            return productRepository.findAll()
        }
    }

