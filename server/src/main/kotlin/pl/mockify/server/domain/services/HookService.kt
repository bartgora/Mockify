package pl.mockify.server.domain.services

import pl.mockify.server.domain.Hook

interface HookService {

    fun saveHook(hook: Hook): Hook

    fun getHook(customName: String): Hook?
}