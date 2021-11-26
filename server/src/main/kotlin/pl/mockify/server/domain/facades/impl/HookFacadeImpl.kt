package pl.mockify.server.domain.facades.impl

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import pl.mockify.server.domain.Event
import pl.mockify.server.domain.HookFactory
import pl.mockify.server.domain.Request
import pl.mockify.server.domain.Response
import pl.mockify.server.domain.facades.HookFacade
import pl.mockify.server.domain.services.HookService

@Component
class HookFacadeImpl(private val jpaHookService: HookService, private val hookFactory: HookFactory) : HookFacade {

    override fun processRequest(
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
        var exitingHook = jpaHookService.getHook(name)
        if (exitingHook == null) {
            if (method == HttpMethod.GET) {
                exitingHook = jpaHookService.saveHook(hookFactory.createNewHook(name, body, headers, HttpMethod.GET))
            } else {
                throw IllegalStateException("No Hook!")
            }
        } else {
            exitingHook.addEvent(createEvent(method, body, headers, exitingHook.responseTemplate))
            jpaHookService.saveHook(exitingHook)
        }
        return exitingHook.events.map { event -> event.response }.last()
    }

    private fun createRequest(method: HttpMethod, body: Map<String, String>?, headers: Map<String, String>): Request {
        return Request(method, body, headers)
    }

    override fun getEvents(name: String): List<Event> {
        return jpaHookService.getHook(name)!!.events
    }

    override fun updateResponse(name: String, body: Map<String, String>): Response {
        val hook = jpaHookService.getHook(name)
        val newResponse = Response(body)
        hook?.responseTemplate = newResponse
        if (hook != null) {
            jpaHookService.updateResponse(hook)
        }
        return newResponse
    }
}