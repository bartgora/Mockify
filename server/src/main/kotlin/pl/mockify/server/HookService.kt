package pl.mockify.server

interface HookService {

    fun saveHook(hook: Hook)

    fun getHook(customName: String): Hook?
}