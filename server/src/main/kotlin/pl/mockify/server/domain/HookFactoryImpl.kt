package pl.mockify.server.domain

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class HookFactoryImpl : HookFactory {

    override fun createNewHook(
            name: String,
            body: Map<String, String>?,
            headers: Map<String, String>,
            method: HttpMethod
    ): Hook {
        val request = Request(method, body, headers)
        val defaultTemplate = Response(body = mapOf(Pair("status", "ok")))
        val event = Event(request, defaultTemplate)
        return Hook(name, defaultTemplate, listOf(event))
    }
}