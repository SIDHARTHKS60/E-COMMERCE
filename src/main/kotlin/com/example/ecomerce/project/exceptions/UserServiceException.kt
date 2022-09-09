package com.example.ecomerce.project.exceptions

import org.springframework.stereotype.Component


class UserServiceException(message: String) : RuntimeException(message) {

}

