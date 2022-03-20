package pl.mockify.server.domain.facades

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import pl.mockify.server.domain.Event
import pl.mockify.server.domain.HookFactory
import pl.mockify.server.domain.Request
import pl.mockify.server.domain.Response
import pl.mockify.server.domain.services.HookService
import java.time.LocalDateTime

@Component
class HookFacade(private val hookService: HookService, private val hookFactory: HookFactory) {

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

    fun updateResponse(name: String, body: Map<String, String>): Response {
        val hook = hookService.getHook(name) ?: throw IllegalStateException("No Hook!")
        val newResponse = Response(body)
        hook.responseTemplate = newResponse
        hookService.updateResponse(hook)

        return newResponse
    }

    fun getEvents(name: String): List<Event> {
        val hook = hookService.getHook(name)?: throw IllegalStateException("No Hook!")
        return hook.events
    }

    private fun processWithRequestBody(
        name: String,
        body: Map<String, String>?,
        headers: Map<String, String>,
        method: HttpMethod
    ): Response {
        val exitingHook = hookService.getHook(name)
        if (exitingHook == null) {
            throw IllegalStateException("No hook!")
        } else {
            exitingHook.addEvent(createEvent(method, body, headers, exitingHook.responseTemplate))
            hookService.saveHook(exitingHook)
        }

        return exitingHook.events.map { event -> event.response }.last()
    }

    private fun createEvent(
        method: HttpMethod,
        body: Map<String, String>?,
        headers: Map<String, String>,
        responseTemplate: Response
    ): Event {
        LocalDateTime.now();
        return Event(createRequest(method, body, headers), responseTemplate, LocalDateTime.now())
    }

    private fun processEmptyRequestBody(
        name: String,
        body: Map<String, String>?,
        headers: Map<String, String>,
        method: HttpMethod
    ): Response {
        var existingHook = hookService.getHook(name)
        if (existingHook == null) {
            if (method == HttpMethod.GET) {
                existingHook = hookService.saveHook(hookFactory.createNewHook(name, body, headers, HttpMethod.GET))
            } else {
                throw IllegalStateException("No Hook!")
            }
        } else {
            existingHook.addEvent(createEvent(method, body, headers, existingHook.responseTemplate))
            hookService.saveHook(existingHook)
        }
        return existingHook.events.map { event -> event.response }.last()
    }

    private fun createRequest(method: HttpMethod, body: Map<String, String>?, headers: Map<String, String>): Request {
        return Request(method, body, headers)
    }
}