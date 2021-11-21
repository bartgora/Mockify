package pl.mockify.server.domain.converters

import pl.mockify.server.domain.Event
import pl.mockify.server.domain.Hook
import pl.mockify.server.domain.Response
import pl.mockify.server.domain.bodyToString
import pl.mockify.server.data.Event as DBEvent
import pl.mockify.server.data.Hook as DBHook
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
    dbResponse.body = response.bodyToString().toString()
    return dbResponse
}

fun convertEventsToDB(events: List<Event>): List<DBEvent> {
    return events.map { event -> convertEventToDB(event) }

}

fun convertEventToDB(event: Event): DBEvent {

    val dbEvent = DBEvent()
//    dbEvent.request = event.request
    dbEvent.response = convertResponseToDB(event.response)
    return dbEvent
}


fun convertHookFromDB(hook: DBHook): Hook {
    return pl.mockify.server.domain.Hook(hook.name, /*hook.responseTemplate, hook.events*/)
}

