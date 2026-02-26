package com.kotlinspring.controller

import com.kotlinspring.service.GreetingsService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/greet")
class GreetController(val greetingsService: GreetingsService) {
    @RequestMapping("/")
    fun greet(): String {
        return "Hello, World!"
    }

    @RequestMapping("/{name}")
    fun greetWithName(@PathVariable("name") name: String): String {
        return greetingsService.retrieveGreeting(name)
    }
}