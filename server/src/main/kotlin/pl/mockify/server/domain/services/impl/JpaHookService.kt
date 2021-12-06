package pl.mockify.server.domain.services.impl

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import pl.mockify.server.data.HookRepository
import pl.mockify.server.domain.Hook
import pl.mockify.server.domain.converters.*
import pl.mockify.server.domain.services.HookService
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class JpaHookService(private var hookRepository: HookRepository) : HookService {

    override suspend fun saveHook(hook: Hook): Hook {
        return runBlocking {
            val asyncExistingHook = async { hookRepository.getByName(hook.name) }
            val existingHook = asyncExistingHook.await()
            if (existingHook != null) {
                existingHook.lastModified = Timestamp.valueOf(LocalDateTime.now())
                existingHook.responseTemplate = bodyToString(hook.responseTemplate.body)
                existingHook.events = existingHook.events.plus(convertEventToDB(hook.events.last()))
                val asyncSavedHook = async { hookRepository.save(existingHook) }
                val savedHook = asyncSavedHook.await()
                return@runBlocking convertHookFromDB(savedHook)
            }
            return@runBlocking convertHookFromDB(hookRepository.save(convertHookToDB(hook)))
        }
    }

    override suspend fun getHook(customName: String): Hook {
        return runBlocking {
            val asyncHook =  async {hookRepository.getByName(customName)}
            val hook = asyncHook.await() ?: throw IllegalStateException("No Hook found")
            return@runBlocking convertHookFromDB(hook)
        }
    }

    override suspend fun updateResponse(hook: Hook): Hook {
        return runBlocking {
            val asyncExistingHook = async {
                hookRepository.getByName(hook.name)
            }
            val existingHook = asyncExistingHook.await()
            existingHook?.responseTemplate = bodyToString(hook.responseTemplate.body)
            val asyncSavedHook = async { hookRepository.save(existingHook!!) }
            val saveHook = asyncSavedHook.await()
            return@runBlocking convertHookFromDB(saveHook)
        }
    }
}