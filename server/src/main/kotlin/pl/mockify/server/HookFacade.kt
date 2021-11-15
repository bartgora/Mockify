package pl.mockify.server

import org.springframework.http.HttpMethod

interface HookFacade {

    fun processRequest(name: String, body: Map<String, String>?, headers: Map<String, String>, method: HttpMethod): Response

    fun getEvents(name: String): List<Event>

    fun updateResponse(name: String, body: Map<String, String>): Response
}