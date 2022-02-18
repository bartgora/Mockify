package pl.mockify.server.domain

import org.springframework.http.HttpMethod
import java.time.LocalDateTime

class Hook(val name: String, var responseTemplate: Response, var events: List<Event>) {

    fun addEvent(event: Event) {
        events = events.plus(event)
    }
}

class Request(val method: HttpMethod, val body: Map<String, String>?, val headers: Map<String, String>)

class Response(val body: Map<String, String>)

class Event(val request: Request, val response: Response, val timestamp: LocalDateTime)