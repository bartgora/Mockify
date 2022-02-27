package pl.mockify.server

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod

class HookFacadeFunctionalTest : TestBase() {

    @Test
    fun `should create new Hook`() {
        //given
        val givenHookName = "givenHookName"

        //when
        hookFacade.processRequest(name = givenHookName,body = null, headers = emptyMap(), HttpMethod.GET )

        //then
        val hook = hookRepository.findByName(givenHookName)
        assert(hook.isPresent)
        hook.ifPresent {
            it.name shouldBe givenHookName
            it.events.size shouldBe 1
    }


    }
}