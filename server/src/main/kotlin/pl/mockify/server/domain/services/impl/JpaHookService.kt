package pl.mockify.server.domain.services.impl

import org.springframework.stereotype.Service
import pl.mockify.server.data.HookRepository
import pl.mockify.server.domain.Hook
import pl.mockify.server.domain.converters.bodyToString
import pl.mockify.server.domain.converters.convertEventToDB
import pl.mockify.server.domain.converters.convertHookFromDB
import pl.mockify.server.domain.converters.convertHookToDB
import pl.mockify.server.domain.services.HookService
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class JpaHookService(private var hookRepository: HookRepository) : HookService {

    override fun saveHook(hook: Hook): Hook {
        val existingHook = hookRepository.getByName(hook.name)
        if (existingHook != null) {
            existingHook.lastModified = Timestamp.valueOf(LocalDateTime.now())
            existingHook.responseTemplate = bodyToString(hook.responseTemplate.body)
            existingHook.events = existingHook.events.plus(convertEventToDB(hook.events.last()))
            return convertHookFromDB(hookRepository.save(existingHook))
        }
        return convertHookFromDB(hookRepository.save(convertHookToDB(hook)))
    }

    override fun getHook(customName: String): Hook? {
        val hook = hookRepository.getByName(customName) ?: return null
        return convertHookFromDB(hook)
    }

    override fun updateResponse(hook: Hook): Hook {
        val existingHook = hookRepository.getByName(hook.name)
        existingHook?.responseTemplate = bodyToString(hook.responseTemplate.body)
        return convertHookFromDB(hookRepository.save(existingHook!!))
    }
}