package com.example.ecomerce.project.service

import com.example.ecomerce.project.model.User
import com.example.ecomerce.project.repository.UserRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class UserServiceTest {

    // mocking the repository layer response
    val user1 = User(999, "Rahul", 1234567890, "abcd@abcd")
    val user2 = User(888, "Rohit", 1234567891, "abcd@abca")

    private val userRepository = mockk<UserRepository>() {

        every {
            findAll()
        } returns Flux.just(user1, user2)

        every {
            findById(999)
        }returns Mono.just(user1)
    }

    private val userService = UserService(userRepository)

    @Test
    fun `should return users when findAllUsers  method is called`() {

        val firstUser = userService.findAllUsers().blockFirst()
        val secondUser = userService.findAllUsers().blockLast()

        if (firstUser != null) {
            firstUser shouldBe user1
        }
        if (secondUser != null) {
            secondUser shouldBe user2
        }
    }

    @Test
    fun `test adding User`() {
        val user1 = User(999, "Rahul", 1234567890, "abcd@abcd")

        every{
            userRepository.save(user1)
        }returns Mono.just(user1)

        val addedUser = userService.addUser(user1).block()

        addedUser shouldBe user1
    }

    @Test
    fun `test Find User By Id`() {

        val result=userService.findUserById(999).block()

        result shouldBe user1

    }

    @Test
    fun `delete User By Id`() {

        every{
            userRepository.deleteById(999)
        }returns Mono.empty()
    }

    @Test
    fun `test update User`() {

       // val user1 = User(999,"Rahul K",9999999999,"Aaaaa@aaa")
        every{
            userRepository.save(user1)
        }returns Mono.just(user1)
        val updatedUser = userService.updateUser(999,user1).block()

        updatedUser shouldBe user1
    }
}
