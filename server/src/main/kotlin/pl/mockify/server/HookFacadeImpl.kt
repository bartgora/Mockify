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
        }

    }

    private fun processGet(name: String, body: Map<String, String>?, headers: Map<String, String>) {
        var exitingHook = hookService.getHook(name)
        if (exitingHook == null) {
            hookService.saveHook(createNeHook(name, body, headers))
        } else {
            exitingHook.requests = exitingHook.requests.plus(createRequest(body, headers))
            hookService.saveHook(exitingHook)

        }

    }

    private fun createRequest(body: Map<String, String>?, headers: Map<String, String>): Request {
        return Request(body, headers)
    }

    private fun createNeHook(name: String, body: Map<String, String>?, headers: Map<String, String>): Hook {
        var request = Request(body, headers)
        return Hook(name, listOf(request))
    }

    override fun getResponses(name: String): List<Request> {
        return hookService.getHook(name)!!.requests;
    }
}