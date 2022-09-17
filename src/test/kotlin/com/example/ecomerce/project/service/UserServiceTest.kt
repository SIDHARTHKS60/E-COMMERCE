package com.example.ecomerce.project.service

import com.example.ecomerce.project.model.User
import com.example.ecomerce.project.repository.UserRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class UserServiceTest {

    // mocking the repository layer response
    val user1 = User(999, "Rahul", 1234567890, "abcd@abcd")
    val user2 = User(888, "Rohit", 1234567891, "abcd@abca")

    private val userRepository = mockk<UserRepository>() {

       /* every {
            save(user1)
        } returns Mono.just(user1)
*/
        every {
            findAll()
        } returns Flux.just(user1, user2)

        every {
            findById(888)
        }returns Mono.just(user2)

        /*every {
            deleteById(999)
        }*/
         }

    private val userService = UserService(userRepository)

    /*@Test
    fun `should add user to repsitory`(){

        val firstUser=userService.addUser(user1)
        if(firstUser != null){firstUser shouldBe user1}

    }*/
    @Test
    fun `should return users when find  method is called`() {

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
    fun `should expect on complete call post all the Users are retrieved`() {
        //StepVerifier takes care of subscribing
        StepVerifier.create(userService.findAllUsers())
            .expectSubscription()
            .expectNext(user1)
            .expectNext(user2)
            .verifyComplete()
        StepVerifier.create(userService.findAllUsers())
            .expectNextCount(2)
            .verifyComplete()
    }

   @Test
    fun `should delete the user on the basis of the id`() {

        val result = userService.deleteUserById(999)

        StepVerifier.create(userService.deleteUserById(999))
            .expectSubscription()
            .verifyComplete()

    }
}


