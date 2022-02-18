package pl.mockify.server.domain.converters

import org.springframework.http.HttpMethod
import pl.mockify.server.domain.*
import java.sql.Timestamp
import java.time.LocalDateTime
import pl.mockify.server.data.Event as DBEvent
import pl.mockify.server.data.Hook as DBHook
import pl.mockify.server.data.Request as DBRequest
import pl.mockify.server.data.Response as DBResponse

fun convertHookToDB(hook: Hook): DBHook {
    val dbHook = DBHook()
    dbHook.lastModified = Timestamp.valueOf(LocalDateTime.now())
    dbHook.name = hook.name
    dbHook.responseTemplate = bodyToString(hook.responseTemplate.body)
    dbHook.events = convertEventsToDB(hook.events)
    return dbHook
}

fun convertResponseToDB(response: Response): DBResponse {

    val dbResponse = DBResponse()
    dbResponse.body = bodyToString(response.body)
    return dbResponse
}

fun convertEventsToDB(events: List<Event>): List<DBEvent> {
    return events.map { event -> convertEventToDB(event) }
}

fun convertEventToDB(event: Event): DBEvent {

    val dbEvent = DBEvent()
    dbEvent.request = convertRequestToDB(event.request)
    dbEvent.response = convertResponseToDB(event.response)
    dbEvent.timestamp = Timestamp.valueOf(event.timestamp)
    return dbEvent
}

fun convertRequestToDB(request: Request): DBRequest {
    val dbRequest = DBRequest()
    dbRequest.headers = bodyToString(request.headers)
    dbRequest.method = request.method.toString()
    dbRequest.body = request.body?.let { bodyToString(it) }
    return dbRequest;
}

fun convertHookFromDB(dbHook: DBHook): Hook {
    val name = dbHook.name
    val response = Response(dbHook.responseTemplate.stringToBody())
    val events = convertDBEventsToEvents(dbHook.events)
    return Hook(name, response, events)
}

fun convertDBResponseToResponse(dbResponse: DBResponse): Response {
    return Response(dbResponse.body?.stringToBody() ?: emptyMap())
}

fun convertDBRequest(dbRequest: DBRequest): Request {
    return Request(
        HttpMethod.valueOf(dbRequest.method),
        dbRequest.body?.stringToBody(),
        dbRequest.headers.stringToBody()
    )
}

fun convertDBEventsToEvents(dbEvents: List<DBEvent>): List<Event> {
    return dbEvents.map { dbEvent -> convertDbEventToEvent(dbEvent) }
}

fun convertDbEventToEvent(dbEvent: DBEvent): Event {
    val response = convertDBResponseToResponse(dbEvent.response)
    val request = convertDBRequest(dbEvent.request)
    return Event(response = response, request = request, timestamp = dbEvent.timestamp.toLocalDateTime())
}

