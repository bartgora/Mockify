package pl.mockify.server

import org.springframework.http.HttpMethod

class Hook(val name: String, var requests : List<Request>)

class Request(val method: HttpMethod, val body : Map<String, String>?, val headers : Map<String, String>)
