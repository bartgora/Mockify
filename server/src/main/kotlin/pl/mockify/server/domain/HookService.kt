package pl.mockify.server.domain

interface HookService {

    fun saveHook(hook: Hook): Hook

    fun getHook(customName: String): Hook?
}