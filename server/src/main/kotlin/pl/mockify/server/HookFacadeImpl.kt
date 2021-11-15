package pl.mockify.server

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class HookFacadeImpl(private val hookService: HookService) : HookFacade {

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
            else -> throw IllegalStateException();
        }
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
            exitingHook.events =
                exitingHook.events.plus(createEvent(method, body, headers, exitingHook.responseTemplate))
            hookService.saveHook(exitingHook)

        }
        return exitingHook?.events.map { event -> event.response }.last()
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
        var exitingHook = hookService.getHook(name)
        if (exitingHook == null) {
            if (method == HttpMethod.GET) {
                exitingHook = hookService.saveHook(createNewHook(name, body, headers, HttpMethod.GET))
            } else {
                throw IllegalStateException("No Hook!")
            }
        } else {
            exitingHook.events =
                exitingHook.events.plus(createEvent(method, body, headers, exitingHook.responseTemplate))
            hookService.saveHook(exitingHook)

        }
        return exitingHook?.events.map { event -> event.response }.last()
    }

    private fun createRequest(method: HttpMethod, body: Map<String, String>?, headers: Map<String, String>): Request {
        return Request(method, body, headers)
    }

    private fun createNewHook(
        name: String,
        body: Map<String, String>?,
        headers: Map<String, String>,
        method: HttpMethod
    ): Hook {
        val request = Request(method, body, headers)
        val defaultTemplate = Response(body = mapOf(Pair("status", "ok")))
        val event = Event(request, defaultTemplate);
        return Hook(name, defaultTemplate, listOf(event))
    }

    override fun getEvents(name: String): List<Event> {
        return hookService.getHook(name)!!.events

    }

    override fun updateResponse(name: String, body: Map<String, String>): Response {
        val hook = hookService.getHook(name)
        val newResponse = Response(body)
        hook?.responseTemplate = newResponse
        return newResponse
    }
}