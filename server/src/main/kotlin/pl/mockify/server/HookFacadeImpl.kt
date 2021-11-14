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
            HttpMethod.GET -> processGet(name, body, headers)
            HttpMethod.POST -> processPost(name,body, headers)
        }

    }

    private fun processPost(name: String, body: Map<String, String>?, headers: Map<String, String>) {
        var exitingHook = hookService.getHook(name)
        if (exitingHook == null) {
            throw IllegalStateException("No hook!")
        } else {
            exitingHook.requests = exitingHook.requests.plus(createRequest(HttpMethod.POST, body, headers))
            hookService.saveHook(exitingHook)

        }
    }

    private fun processGet(name: String, body: Map<String, String>?, headers: Map<String, String>) {
        var exitingHook = hookService.getHook(name)
        if (exitingHook == null) {
            hookService.saveHook(createNeHook(name, body, headers, HttpMethod.GET))
        } else {
            exitingHook.requests = exitingHook.requests.plus(createRequest(HttpMethod.GET, body, headers))
            hookService.saveHook(exitingHook)

        }

    }

    private fun createRequest(method: HttpMethod, body: Map<String, String>?, headers: Map<String, String>): Request {
        return Request(method, body, headers)
    }

    private fun createNeHook(name: String, body: Map<String, String>?, headers: Map<String, String>, method: HttpMethod): Hook {
        var request = Request(method, body, headers)
        return Hook(name, listOf(request))
    }

    override fun getResponses(name: String): List<Request> {
        return hookService.getHook(name)!!.requests;
    }
}