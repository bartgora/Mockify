package pl.mockify.server.domain.services

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

import pl.mockify.server.TestBase
import pl.mockify.server.data.*
import pl.mockify.server.domain.converters.convertHookFromDB
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.transaction.Transactional

internal class HookServiceTest(
    private var hookRepository: HookRepository,
    private var hookService: HookService
) : TestBase() {

    @Test
    @Transactional
    fun removeEvents() {

        //given
        val givenHook = givenHook()


        //when
        hookRepository.save(givenHook)
        hookService.removeEvents(convertHookFromDB(givenHook))

        //then
        val result = hookRepository.findByName("bartek")
        result shouldBe null

    }

    private fun givenHook(): Hook{
        var givenHook = Hook()
        givenHook.name = "bartek"
        givenHook.responseTemplate = "{}"
        val givenEvent = Event()
        givenEvent.timestamp = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))
        givenEvent.response = Response()
        givenEvent.request = Request().apply {
            method = "GET"
            headers = "{}"
        }

        givenHook.events = listOf(givenEvent)

        return givenHook
    }
}