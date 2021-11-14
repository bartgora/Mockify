package pl.mockify.server

import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class WebhookController(private var hookFacade: HookFacade) {


    @GetMapping("/hook/{name}")
    fun createWebhook(@PathVariable name: String,
                      @RequestHeader headers : Map<String, String>): ResponseEntity<List<Request>> {
        hookFacade.processRequest(name, null, headers, HttpMethod.GET)
        return ResponseEntity.ok().body(hookFacade.getResponses(name))
    }
}