package com.example.ecomerce.project.controller

import com.example.ecomerce.project.model.User
import com.example.ecomerce.project.service.UserService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(UserController::class)
@AutoConfigureWebTestClient
class UserControllerTest {
    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var userService: UserService

    @Test
    fun `should return list of users`() {
        val user1 = User(999, "Rahul", 1234567890, "abcd@abcd")
        val user2 = User(888, "Ramesh", 1234567891, "abcd@abca")

        val expectedResult = listOf(
            mapOf(
                "userId" to 999,
                "userName" to "Rahul",
                "userContactno" to 1234567890,
                "userPassword" to "abcd@abcd"
            ),
            mapOf(
                "userId" to 888,
                "userName" to "Ramesh",
                "userContactno" to 1234567891,
                "userPassword" to "abcd@abca"
            ),
        )

        every {
            userService.findAllUsers()
        } returns Flux.just(user1, user2)

        val response = client.get()
            .uri("/users/lists")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody


        response.blockFirst() shouldBe expectedResult[0]
        //response.blockLast() shouldBe expectedResult[1]

        verify(exactly = 1) {
            userService.findAllUsers()
        }
    }

    @Test
    fun `should create user when create api is being called`() {

        val exepectedResponse = mapOf(
            "userId" to 999,
            "userName" to "Rahul",
            "userContactno" to 1234567890,
            "userPassword" to "abcd@abcd")

        val user = User(999, "Rahul", 1234567890, "abcd@abcd")

        every {
            userService.addUser(user)
        } returns Mono.just(user)

        val response = client.post()
            .uri("/users/add")
            .bodyValue(user)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe exepectedResponse

        verify(exactly = 1) {
            userService.addUser(user)
        }
    }

    @Test
    fun `should be able to update the user`() {

        val expectedResult =mapOf(
            "userId" to 999,
            "userName" to "Rahul K",
            "userContactno" to 1234567890,
            "userPassword" to "abcd@abcd" )

        val user = User(999,"Rahul K" ,1234567890 , "abcd@abcd")

        every {
            userService.updateUser(999,user)
        } returns Mono.just(user)

        val response = client.put()
            .uri("/users/update/999")
            .bodyValue(user)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            userService.updateUser(999,user)
        }
    }

    @Test
    fun `should be able to delete the user`() {

        val expectedResult = listOf(
             mapOf( "id" to 999,
                 "name" to "Rahul",
                 "contactno" to 1234567890,
                 "password" to "abcd@abcd" ) )

        val user = User(999,"Rahul" ,1234567890 , "abcd@abcd")

         every {
         userService.deleteUserById(999) }returns  Mono.empty()

         val response = client.delete()
             .uri("/users/delete/999")
             .exchange()
             .expectStatus().is2xxSuccessful

         verify(exactly = 1) {
             userService.deleteUserById(999)
        }
   }

    @Test
    fun `should return a single user`() {

        val exepectedResponse = mapOf(
            "userId" to 999,
            "userName" to "Rahul K",
            "userContactno" to 1234567890,
            "userPassword" to "Aaaaaa@aaa")

        val user1=User(999,"Rahul K",1234567890,"Aaaaaa@aaa")

        every {
            userService.findUserById(999)
        } returns Mono.just(user1)



        val response = client.get()
            .uri("/users/find/999")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody

        response.blockFirst() shouldBe exepectedResponse

        verify(exactly = 1) {
            userService.findUserById(999)
        }

    }
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun userService() = mockk<UserService>()
        }

}
//




































//Sidharth KS Saurabh