package pl.mockify.server.domain.facades

import org.springframework.http.HttpMethod
import pl.mockify.server.domain.Event
import pl.mockify.server.domain.Response

interface HookFacade {

    suspend fun processRequest(name: String,
                       body: Map<String, String>?,
                       headers: Map<String, String>,
                       method: HttpMethod): Response

    suspend fun getEvents(name: String): List<Event>

    suspend fun updateResponse(name: String, body: Map<String, String>): Response
}