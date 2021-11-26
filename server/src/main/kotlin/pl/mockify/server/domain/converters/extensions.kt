package pl.mockify.server.domain.converters

import com.fasterxml.jackson.databind.ObjectMapper

fun bodyToString(map: Map<String, String>): String {
    val objectMapper = ObjectMapper()
    return objectMapper.writeValueAsString(map)
}

fun String.stringToBody(): Map<String, String> {
    val objectMapper = ObjectMapper()
    return objectMapper.readValue(this, Map::class.java) as Map<String, String>
}