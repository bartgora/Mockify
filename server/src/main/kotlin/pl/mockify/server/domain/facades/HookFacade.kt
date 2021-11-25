package pl.mockify.server.domain.facades

import org.springframework.http.HttpMethod
import pl.mockify.server.domain.Event
import pl.mockify.server.domain.Response

interface HookFacade {

    fun processRequest(name: String,
                       body: Map<String, String>?,
                       headers: Map<String, String>,
                       method: HttpMethod): Response

    fun getEvents(name: String): List<Event>

    fun updateResponse(name: String, body: Map<String, String>): Response
}