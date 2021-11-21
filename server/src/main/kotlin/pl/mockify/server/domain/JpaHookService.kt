package pl.mockify.server.domain

import org.springframework.stereotype.Service
import pl.mockify.server.data.HookRepository
import pl.mockify.server.domain.converters.convertHookFromDB
import pl.mockify.server.domain.converters.convertHookToDB

@Service
class JpaHookService(private var hookRepository: HookRepository) : HookService {
    override fun saveHook(hook: Hook): Hook {
        return convertHookFromDB(hookRepository.save(convertHookToDB(hook)))
    }

    override fun getHook(customName: String): Hook? {
        TODO("Not yet implemented")
    }
}