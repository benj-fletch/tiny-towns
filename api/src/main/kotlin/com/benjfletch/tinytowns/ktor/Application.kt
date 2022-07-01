package com.benjfletch.tinytowns.ktor

import com.benjfletch.tinytowns.Serializer
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.cio.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json(Serializer.Model)
    }
    routing {
        registerBoardRoutes()
    }
}
