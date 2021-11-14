package pl.mockify.server

import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class WebhookController(private var hookFacade: HookFacade) {


    @RequestMapping("/hook/{name}", method = [RequestMethod.GET, RequestMethod.DELETE])
    fun getWebhook(
        @PathVariable name: String,
        @RequestHeader headers: Map<String, String>
    ): ResponseEntity<List<Request>> {
        hookFacade.processRequest(name, null, headers, HttpMethod.GET)
        return ResponseEntity.ok().body(hookFacade.getResponses(name))
    }

    @RequestMapping("/hook/{name}", method = [RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT])
    fun postWebhook(
        @PathVariable name: String,
        @RequestBody body: Map<String, String>,
        @RequestHeader headers: Map<String, String>,
        servletRequest: HttpServletRequest
    ): ResponseEntity<List<Request>> {
        hookFacade.processRequest(name, body, headers, HttpMethod.valueOf(servletRequest.method))
        return ResponseEntity.ok().body(hookFacade.getResponses(name))
    }
}