package pl.mockify.server.domain.converters

import pl.mockify.server.domain.*
import pl.mockify.server.data.Event as DBEvent
import pl.mockify.server.data.Hook as DBHook
import pl.mockify.server.data.Request as DBRequest
import pl.mockify.server.data.Response as DBResponse

fun convertHookToDB(hook: Hook): DBHook {
    val dbHook = DBHook()
    dbHook.name = hook.name
    dbHook.responseTemplate = convertResponseToDB(hook.responseTemplate)
    dbHook.events = convertEventsToDB(hook.events)
    return dbHook
}

fun convertResponseToDB(response: Response): DBResponse {

    val dbResponse = DBResponse()
    dbResponse.body = response.body.bodyToString().toString()
    return dbResponse
}

fun convertEventsToDB(events: List<Event>): List<DBEvent> {
    return events.map { event -> convertEventToDB(event) }
}

fun convertEventToDB(event: Event): DBEvent {

    val dbEvent = DBEvent()
    dbEvent.request = convertRequestToDB(event.request)
    dbEvent.response = convertResponseToDB(event.response)
    return dbEvent
}

fun convertRequestToDB(request: Request): DBRequest {
    val dbRequest = DBRequest()
    dbRequest.headers = request.headers.bodyToString().toString()
    return dbRequest;
}

fun convertHookFromDB(hook: DBHook): Hook {
    val name = hook.name
    hook.responseTemplate
    val result = Hook(name)
    return result
}

