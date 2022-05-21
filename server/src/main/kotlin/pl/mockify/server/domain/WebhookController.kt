package pl.mockify.server.domain

import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.mockify.server.domain.facades.HookFacade
import javax.servlet.http.HttpServletRequest

@RestController
class WebhookController(private var hookFacade: HookFacade) {

    @RequestMapping("/hook/{name}", method = [RequestMethod.GET, RequestMethod.DELETE])
    fun bodyLessWebhook(
            @PathVariable name: String,
            @RequestHeader headers: Map<String, String>,
            servletRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(hookFacade.processRequest(name, null, headers, HttpMethod.valueOf(servletRequest.method)).body)
    }



    @RequestMapping("/hook/{name}", method = [RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT])
    fun bodyWebhook(
            @PathVariable name: String,
            @RequestBody body: Map<String, String>,
            @RequestHeader headers: Map<String, String>,
            servletRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
                hookFacade.processRequest(
                        name,
                        body,
                        headers,
                        HttpMethod.valueOf(servletRequest.method)
                ).body
        )
    }

    @GetMapping("/hook/{name}/events")
    fun getEvents(@PathVariable name: String): ResponseEntity<List<Event>> {
        return ResponseEntity.ok(hookFacade.getEvents(name))
    }

    @PatchMapping("/hook/{name}/response")
    fun patchResponse(
            @PathVariable name: String,
            @RequestBody body: Map<String, String>
    ): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(hookFacade.updateResponse(name, body).body)
    }
}