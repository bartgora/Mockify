package pl.mockify.server.domain.facades

import kotlinx.coroutines.*
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import pl.mockify.server.domain.*
import pl.mockify.server.domain.services.HookService
import java.time.LocalDateTime

@Component
class HookFacade(private val hookService: HookService, private val hookFactory: HookFactory) {

    suspend fun processRequest(
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

     suspend fun updateResponse(name: String, body: Map<String, String>): Response {
        val hook = withContext(Dispatchers.IO) {
            hookService.getHook(name)
        } ?: throw IllegalStateException("No Hook!")
        val newResponse = Response(body)
        hook.responseTemplate = newResponse
         withContext(Dispatchers.IO) {
             hookService.updateResponse(hook)
         }

        return newResponse
    }

    suspend fun getEvents(name: String): List<Event> {
        val hook = runBlocking {
            async(Dispatchers.IO) {
                hookService.getHook(name) ?: throw IllegalStateException("No Hook!")
            }

        }
        return hook.await().events
    }

    suspend fun deleteEvents(name: String) {
        runBlocking {
            val hook = async(Dispatchers.IO) {
                hookService.getHook(name) ?: throw IllegalStateException("No Hook!")
            }
            withContext(Dispatchers.IO) {
                hookService.removeEvents(hook.await())
            }

        }
    }

    private suspend fun processWithRequestBody(
            name: String,
            body: Map<String, String>?,
            headers: Map<String, String>,
            method: HttpMethod
    ): Response {
        val existingHookDeferred = runBlocking { async(Dispatchers.IO) { hookService.getHook(name) } }
        val exitingHook = existingHookDeferred.await()
        if (exitingHook == null) {
            throw IllegalStateException("No hook!")
        } else {
            exitingHook.addEvent(createEvent(method, body, headers, exitingHook.responseTemplate))
            withContext(Dispatchers.IO) {
                hookService.saveHook(exitingHook)
            }
        }

        return exitingHook.events.map { event -> event.response }.last()
    }

    private suspend fun createEvent(
            method: HttpMethod,
            body: Map<String, String>?,
            headers: Map<String, String>,
            responseTemplate: Response
    ): Event {
        return Event(createRequest(method, body, headers), responseTemplate, LocalDateTime.now())
    }

    private suspend fun processEmptyRequestBody(
            name: String,
            body: Map<String, String>?,
            headers: Map<String, String>,
            method: HttpMethod
    ): Response {
        val existingHookDeferred = runBlocking { async(Dispatchers.IO) { hookService.getHook(name) } }
        var existingHook = existingHookDeferred.await()
        if (existingHook == null) {
            if (method == HttpMethod.GET) {
                existingHook = withContext(Dispatchers.IO) {
                    hookService.saveHook(hookFactory.createNewHook(name, body, headers, HttpMethod.GET))
                }
            } else {
                throw IllegalStateException("No Hook!")
            }
        } else {
            existingHook.addEvent(createEvent(method, body, headers, existingHook.responseTemplate))
            withContext(Dispatchers.IO) {
                hookService.saveHook(existingHook)
            }
        }
        return existingHook.events.map { event -> event.response }.last()
    }

    private suspend fun createRequest(method: HttpMethod,
                                      body: Map<String, String>?,
                                      headers: Map<String, String>): Request {
        return Request(method, body, headers)
    }
}