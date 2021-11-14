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
    ) {
        when (method) {
            HttpMethod.GET, HttpMethod.DELETE -> processEmptyRequestBody(name, body, headers, method)
            HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH -> processWithRequestBody(name, body, headers, method)
            else -> null;
        }
    }

    private fun processWithRequestBody(
        name: String,
        body: Map<String, String>?,
        headers: Map<String, String>,
        method: HttpMethod
    ) {
        val exitingHook = hookService.getHook(name)
        if (exitingHook == null) {
            throw IllegalStateException("No hook!")
        } else {
            exitingHook.requests = exitingHook.requests.plus(createRequest(method, body, headers))
            hookService.saveHook(exitingHook)

        }
    }

    private fun processEmptyRequestBody(
        name: String,
        body: Map<String, String>?,
        headers: Map<String, String>,
        method: HttpMethod
    ) {
        val exitingHook = hookService.getHook(name)
        if (exitingHook == null) {
            hookService.saveHook(createNeHook(name, body, headers, HttpMethod.GET))
        } else {
            exitingHook.requests = exitingHook.requests.plus(createRequest(method, body, headers))
            hookService.saveHook(exitingHook)

        }

    }

    private fun createRequest(method: HttpMethod, body: Map<String, String>?, headers: Map<String, String>): Request {
        return Request(method, body, headers)
    }

    private fun createNeHook(
        name: String,
        body: Map<String, String>?,
        headers: Map<String, String>,
        method: HttpMethod
    ): Hook {
        val request = Request(method, body, headers)
        return Hook(name, listOf(request))
    }

    override fun getResponses(name: String): List<Request> {
        return hookService.getHook(name)!!.requests
    }
}