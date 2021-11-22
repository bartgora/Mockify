package pl.mockify.server.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpMethod

class Hook(val name: String, var responseTemplate: Response, var events: List<Event>) {

    fun addEvent(event: Event) {
        events.plus(event)
    }
}

class Request(val method: HttpMethod, val body: Map<String, String>?, val headers: Map<String, String>)

class Response(val body: Map<String, String>)

fun Map<String, String>.bodyToString(): String? {
    val objectMapper = ObjectMapper()
    return objectMapper.writeValueAsString(this)
}

class Event(val request: Request, val response: Response)