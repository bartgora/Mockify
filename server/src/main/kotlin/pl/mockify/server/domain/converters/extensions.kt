@file:Suppress("UNCHECKED_CAST")

package pl.mockify.server.domain.converters

import com.fasterxml.jackson.databind.ObjectMapper

fun Map<String, String>.bodyToString(): String {
    val objectMapper = ObjectMapper()
    return objectMapper.writeValueAsString(this)
}

fun String.stringToBody(): Map<String, String> {
    val objectMapper = ObjectMapper()
    return objectMapper.readValue(this, Map::class.java) as Map<String, String>
}