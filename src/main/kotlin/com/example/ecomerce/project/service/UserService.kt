package com.example.ecomerce.project.service

//import com.example.ecomerce.project.exceptions.UserServiceException
import com.example.ecomerce.project.exceptions.UserIdException
import com.example.ecomerce.project.exceptions.UserNotFoundException
import com.example.ecomerce.project.model.User
import com.example.ecomerce.project.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(
    @Autowired
    val userRepository: UserRepository
) {

    fun addUser(user: User): Mono<User> {
        if (user.userId == 0) {
            throw UserIdException("UserId should not be zero")
        }
        return userRepository.save(user)
    }

    fun findAllUsers(): Flux<User> {
        if (userRepository.findAll() == null) {
            throw UserNotFoundException("User not present in Database")
        }
        return userRepository.findAll()
    }

    fun findUserById(userId: Int):Mono<User>{
        return userRepository.findById(userId)
    }

    fun deleteUserById(userId: Int): Mono<Void> {
        if (userId == 0) {
            throw UserIdException("UserId should  not  be zero")
        }
        return userRepository.deleteById(userId)
    }

    fun updateUser(userId: Int, user: User): Mono<User> {
        return userRepository.findById(userId)
            .flatMap {
                it.userId = user.userId
                it.userName = user.userName
                it.userContactno = user.userContactno
                it.userPassword = user.userPassword
                userRepository.save(it)
            }
    }

   /*
   fun findUserByName(name:String):Boolean{
        var a:Boolean
        var orgpass:String
        userRepository.findByName(name)
            .flatMap {
               if( it.password==){
                   a=true
               }
            a=false }

        a=true
        return a
    }*/
}


































///Sidharth - Saurabh 15091240