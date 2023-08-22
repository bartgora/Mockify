package pl.mockify.server.domain.services

import org.springframework.stereotype.Service
import pl.mockify.server.data.EventRepository
import pl.mockify.server.data.HookRepository
import pl.mockify.server.domain.Hook
import pl.mockify.server.domain.converters.*
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class HookService(private var hookRepository: HookRepository, private var eventRepository: EventRepository) {

    @Transactional
    fun saveHook(hook: Hook): Hook {
        hookRepository.findByName(hook.name)?.let {
            it.lastModified = Timestamp.valueOf(LocalDateTime.now())
            it.responseTemplate = hook.responseTemplate.body.bodyToString()
            it.events = it.events.plus(convertEventToDB(hook.events.last()))
            return convertHookFromDB(hookRepository.save(it))
        }

        return convertHookFromDB(hookRepository.save(convertHookToDB(hook)))
    }

    @Transactional
    fun getHook(customName: String): Hook? {
        return hookRepository.findByName(customName)?.let { return convertHookFromDB(it) }
    }

    @Transactional
    fun updateResponse(hook: Hook): Hook {
        val existingHook = hookRepository.findByName(hook.name) ?: throw IllegalStateException("No Hook!")
        existingHook.responseTemplate = hook.responseTemplate.body.bodyToString()
        val saveHook = hookRepository.save(existingHook)
        return convertHookFromDB(saveHook)
    }

    @Transactional
    fun removeEvents(hook: Hook) {
        val existingHook = hookRepository.findByName(hook.name) ?: throw IllegalStateException("No Hook!")
        val events = existingHook.events
        existingHook.events = emptyList()
        eventRepository.deleteAll(events)
    }
}