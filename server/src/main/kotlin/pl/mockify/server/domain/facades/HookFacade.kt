package pl.mockify.server.domain.facades

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import pl.mockify.server.domain.Event
import pl.mockify.server.domain.HookFactory
import pl.mockify.server.domain.Request
import pl.mockify.server.domain.Response
import pl.mockify.server.domain.services.HookService

@Component
class HookFacade(private val jpaHookService: HookService, private val hookFactory: HookFactory) {

    fun processRequest(
        name: String,
        body: Map<String, String>?,
        headers: Map<String, String>,
        method: HttpMethod
    ): Response {
        return when (method) {
            HttpMethod.GET, HttpMethod.DELETE -> processEmptyRequestBody(name, body, headers, method)
            HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH -> processWithRequestBody(
                name,
                body,
                headers,
                method
            )
            else -> throw IllegalStateException()
        }
    }

    private fun processWithRequestBody(
        name: String,
        body: Map<String, String>?,
        headers: Map<String, String>,
        method: HttpMethod
    ): Response {
        val exitingHook = jpaHookService.getHook(name)
        if (exitingHook == null) {
            throw IllegalStateException("No hook!")
        } else {
            exitingHook.addEvent(createEvent(method, body, headers, exitingHook.responseTemplate))
            jpaHookService.saveHook(exitingHook)
        }

        return exitingHook.events.map { event -> event.response }.last()
    }

    private fun createEvent(
        method: HttpMethod,
        body: Map<String, String>?,
        headers: Map<String, String>,
        responseTemplate: Response
    ): Event {
        return Event(createRequest(method, body, headers), responseTemplate)
    }

    private fun processEmptyRequestBody(
        name: String,
        body: Map<String, String>?,
        headers: Map<String, String>,
        method: HttpMethod
    ): Response {
        var existingHook = jpaHookService.getHook(name)
        if (existingHook == null) {
            if (method == HttpMethod.GET) {
                existingHook = jpaHookService.saveHook(hookFactory.createNewHook(name, body, headers, HttpMethod.GET))
            } else {
                throw IllegalStateException("No Hook!")
            }
        } else {
            existingHook.addEvent(createEvent(method, body, headers, existingHook.responseTemplate))
            jpaHookService.saveHook(existingHook)
        }
        return existingHook.events.map { event -> event.response }.last()
    }

    private fun createRequest(method: HttpMethod, body: Map<String, String>?, headers: Map<String, String>): Request {
        return Request(method, body, headers)
    }

    fun getEvents(name: String): List<Event> {
        return jpaHookService.getHook(name).events
    }

    fun updateResponse(name: String, body: Map<String, String>): Response {
        val hook = jpaHookService.getHook(name)
        val newResponse = Response(body)
        hook.responseTemplate = newResponse
        jpaHookService.updateResponse(hook!!)

        return newResponse
    }
}