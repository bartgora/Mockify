package pl.mockify.server.domain.services

import org.springframework.stereotype.Service
import pl.mockify.server.data.EventRepository
import pl.mockify.server.data.HookRepository
import pl.mockify.server.domain.Hook
import pl.mockify.server.domain.converters.bodyToString
import pl.mockify.server.domain.converters.convertEventToDB
import pl.mockify.server.domain.converters.convertHookFromDB
import pl.mockify.server.domain.converters.convertHookToDB
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class HookService(private var hookRepository: HookRepository, private var evenRepository: EventRepository) {

    fun saveHook(hook: Hook): Hook {
        val existingHook = hookRepository.findByName(hook.name)?: null
        if (existingHook !== null) {
            existingHook.lastModified = Timestamp.valueOf(LocalDateTime.now())
            existingHook.responseTemplate = bodyToString(hook.responseTemplate.body)
            existingHook.events = existingHook.events.plus(convertEventToDB(hook.events.last()))
            return convertHookFromDB(hookRepository.save(existingHook))
        }
        return convertHookFromDB(hookRepository.save(convertHookToDB(hook)))
    }

     fun getHook(customName: String): Hook? {
        val hook = hookRepository.findByName(customName)?: return null
        return convertHookFromDB(hook)

    }

     fun updateResponse(hook: Hook): Hook {
        val existingHook = hookRepository.findByName(hook.name)?: throw IllegalStateException("No Hook!")
        existingHook.responseTemplate = bodyToString(hook.responseTemplate.body)
        val saveHook = hookRepository.save(existingHook)
        return convertHookFromDB(saveHook)

    }

    fun removeEvents(hook: Hook){
        val existingHook = hookRepository.findByName(hook.name)?: throw IllegalStateException("No Hook!")
        evenRepository.deleteAll(existingHook.events)
    }
}