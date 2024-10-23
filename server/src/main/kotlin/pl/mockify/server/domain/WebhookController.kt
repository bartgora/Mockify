package pl.mockify.server.domain

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.mockify.server.domain.facades.HookFacade
import javax.servlet.http.HttpServletRequest

@RestController
@Tag(name = "webhook")
class WebhookController(private var hookFacade: HookFacade) {

    @Operation(summary = "Get Webhooks")
    @RequestMapping("/hook/{name}", method = [RequestMethod.GET, RequestMethod.DELETE])
    suspend fun bodyLessWebhook(
            @PathVariable name: String,
            @RequestHeader headers: Map<String, String>,
            servletRequest: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(hookFacade.processRequest(name, null, headers, HttpMethod.valueOf(servletRequest.method)).body)
    }



    @RequestMapping("/hook/{name}", method = [RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT])
    @Operation(summary = "Create Webhook")
    suspend fun bodyWebhook(
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
    @Operation(summary = "Get Webhooks")
    suspend fun getEvents(@PathVariable name: String): ResponseEntity<List<Event>> {
        return ResponseEntity.ok(hookFacade.getEvents(name))
    }

    @DeleteMapping("/hook/{name}/events")
    @Operation(summary = "Delete Webhooks")
    suspend fun deleteEvents(@PathVariable name: String) {
         hookFacade.deleteEvents(name)
    }

    @PatchMapping("/hook/{name}/response")
    @Operation(summary = "Patch Webhooks")
    suspend fun patchResponse(
            @PathVariable name: String,
            @RequestBody body: Map<String, String>
    ): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(hookFacade.updateResponse(name, body).body)
    }
}