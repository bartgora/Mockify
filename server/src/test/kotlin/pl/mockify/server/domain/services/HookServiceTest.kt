package pl.mockify.server.domain.services

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

import pl.mockify.server.TestBase
import pl.mockify.server.data.*
import pl.mockify.server.domain.converters.convertHookFromDB

internal class HookServiceTest(
    private var hookRepository: HookRepository,
    private var hookService: HookService
) : TestBase() {

    @Test
    fun removeEvents() {

        //given
        val givenHook = Hook()
        givenHook.name = "bartek"
        givenHook.responseTemplate = "{}"
        val givenEvent = Event()
        givenEvent.response = Response()
        givenEvent.request = Request().apply {
            method = "GET"
            headers = "{}"
        }

        givenHook.events = listOf(givenEvent)

        //when
        hookRepository.save(givenHook)
        hookService.removeEvents(convertHookFromDB(givenHook))

        //then
        val result = hookRepository.findByName("bartek")
        result shouldNotBe null
        result?.events shouldBe emptyList()


    }
}