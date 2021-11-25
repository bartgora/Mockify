package pl.mockify.server.domain.services.impl

import org.springframework.stereotype.Service
import pl.mockify.server.domain.Hook
import pl.mockify.server.domain.services.HookService

@Service
class HashMapHookService : HookService {

    private val hooks = HashMap<String, Hook>()
    override fun saveHook(hook: Hook): Hook {
        hooks[hook.name] = hook
        return hook
    }

    override fun getHook(customName: String): Hook? {
        if (hooks.contains(customName)) {
            return hooks[customName]
        }
        return null
    }
}