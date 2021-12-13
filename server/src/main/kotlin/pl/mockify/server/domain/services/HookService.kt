package pl.mockify.server.domain.services

import pl.mockify.server.domain.Hook

interface HookService {

    suspend fun saveHook(hook: Hook): Hook

    suspend fun getHook(customName: String): Hook

    suspend fun updateResponse(hook: Hook) : Hook
}