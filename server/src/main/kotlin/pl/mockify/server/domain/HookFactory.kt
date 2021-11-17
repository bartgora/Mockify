package pl.mockify.server.domain

import org.springframework.http.HttpMethod

interface HookFactory {
    fun createNewHook(
        name: String,
        body: Map<String, String>?,
        headers: Map<String, String>,
        method: HttpMethod
    ): Hook
}