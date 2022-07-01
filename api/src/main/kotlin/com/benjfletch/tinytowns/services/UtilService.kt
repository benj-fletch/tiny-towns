package com.benjfletch.tinytowns.services

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import kotlin.reflect.KClass


suspend fun <T: Any> deserializeInputRequest(call: ApplicationCall, targetClass: KClass<T>) : T? {
    return try {
        call.receive(targetClass)
    } catch(e: ContentTransformationException) {
        call.respondText(e.message ?: "Could not parse request body", status = HttpStatusCode.BadRequest)
        null
    } catch (e: Exception) {
        call.respondText(e.message ?: "Unexpected error", status = HttpStatusCode.InternalServerError)
        null
    }
}
