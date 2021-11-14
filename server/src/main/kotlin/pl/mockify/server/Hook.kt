package pl.mockify.server

class Hook(val name: String, var requests : List<Request>)

class Request(val body : Map<String, String>?, val headers : Map<String, String>)
