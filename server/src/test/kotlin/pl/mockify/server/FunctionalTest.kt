package pl.mockify.server

import io.kotest.matchers.shouldBe
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test
import pl.mockify.server.data.HookRepository


class FunctionalTest(private val hookRepository: HookRepository) : TestBase() {

    @Test
    fun `should Add new Hook`() {

        //given
        testHelper.givenGet("/hook/test")

        //when
        val response = testHelper.whenGet("/hook/test")

        //then
        val hook = hookRepository.findByName("test")

        response.statusCode shouldBe HttpStatus.SC_OK
        assert(hook.isPresent)
        hook.ifPresent {
            it.name shouldBe "test"
        }
    }

    @Test
    fun `should Add 2 events to Hook`() {

        //given
        testHelper.givenGet("/hook/test")

        //when
        val response1 = testHelper.whenGet("/hook/test")
        val response2 = testHelper.whenGet("/hook/test")

        //then
        val hook = hookRepository.findByName("test")

        response1.statusCode shouldBe HttpStatus.SC_OK
        response2.statusCode shouldBe HttpStatus.SC_OK

        assert(hook.isPresent)
        hook.ifPresent {
            it.name shouldBe "test"
        }
    }

    @Test
    fun `should Add POST event to Hook`() {

        //given
        val givenBody = "{\"Size\" : \"20\"}"
        testHelper.givenGet("/hook/test")
        testHelper.givenPost("/hook/test", givenBody)

        //when
        testHelper.whenGet("/hook/test")
        val response = testHelper.whenPost("/hook/test", givenBody)

        //then
        val hook = hookRepository.findByName("test")

        response.statusCode shouldBe HttpStatus.SC_OK
        assert(hook.isPresent)
        hook.ifPresent {
            it.name shouldBe "test"
        }
    }

    @Test
    fun `should Add PUT event to Hook`() {

        //given
        val givenBody = "{\"Size\" : \"20\"}"
        testHelper.givenGet("/hook/test")
        testHelper.givenPut("/hook/test", givenBody)

        //when
        testHelper.whenGet("/hook/test")
        val response = testHelper.whenPut("/hook/test", givenBody)

        //then
        val hook = hookRepository.findByName("test")

        response.statusCode shouldBe HttpStatus.SC_OK
        assert(hook.isPresent)
        hook.ifPresent {
            it.name shouldBe "test"

        }
    }

    @Test
    fun `should Add PATCH event to Hook`() {

        //given
        val givenBody = "{\"Size\" : \"20\"}"
        testHelper.givenGet("/hook/test")
        testHelper.givenPatch("/hook/test", givenBody)

        //when
        testHelper.whenGet("/hook/test")
        testHelper.whenPatch("/hook/test", givenBody)
        val response = testHelper.whenPut("/hook/test", givenBody)

        //then
        val hook = hookRepository.findByName("test")

        response.statusCode shouldBe HttpStatus.SC_OK
        assert(hook.isPresent)
        hook.ifPresent {
            it.name shouldBe "test"

        }
    }
}