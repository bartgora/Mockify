package pl.mockify.server

import org.springframework.stereotype.Service

@Service
class HookServiceImpl : HookService {
    val hooks = HashMap<String, Hook>();
    override fun saveHook(hook: Hook) {
        hooks[hook.name] = hook
    }

    override fun getHook(customName: String): Hook? {
        if (hooks.contains(customName)) {
            return hooks[customName]
        }
        return null;
    }
}