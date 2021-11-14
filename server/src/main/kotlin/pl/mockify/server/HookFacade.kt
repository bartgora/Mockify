package pl.mockify.server

import org.springframework.http.HttpMethod

interface HookFacade {

    fun processRequest(name: String, body: Map<String, String>?, headers: Map<String, String>, method: HttpMethod)

    fun getResponses(name: String): List<Request>
}