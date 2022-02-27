package pl.mockify.server

import io.kotest.matchers.shouldBe
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test


class FunctionalTest : TestBase() {

    @Test
    fun `should Add new Hook`() {

        //given
        testHelper.givenGet("/hook/test")

        //when
        testHelper.whenGet("/hook/test")

        //then
        val hook = hookRepository.findByName("test")

        assert(hook.isPresent)
        hook.ifPresent {
            it.name shouldBe "test"
            it.events.size shouldBe 1
            it.events[0].request.method shouldBe "GET"
        }
    }

    @Test
    fun `should Add 2 events to Hook`() {

        //given
        testHelper.givenGet("/hook/test")

        //when
        testHelper.whenGet("/hook/test")
        testHelper.whenGet("/hook/test")

        //then
        val hook = hookRepository.findByName("test")

        assert(hook.isPresent)
        hook.ifPresent {
            it.name shouldBe "test"
            it.events.size shouldBe 2
            it.events[0].request.method shouldBe "GET"
            it.events[1].request.method shouldBe "GET"
        }
    }

    @Test
    fun `should Add POST event to Hook`() {

        //given
        val givenBody = "{\"Size\" : \"20\"}"
        testHelper.givenGet("/hook/test")
        testHelper.givenPost("/hook/test", givenBody)

        //when
        testHelper.whenPost("/hook/test", givenBody)

        //then
        val hook = hookRepository.findByName("test")

        assert(hook.isPresent)
        hook.ifPresent {
            it.name shouldBe "test"
            it.events.size shouldBe 2
            it.events[0].request.method shouldBe "GET"
            it.events[1].request.method shouldBe "POST"
            it.events[1].request.body shouldBe givenBody

        }
    }

    @Test
    fun `should Add PUT event to Hook`() {

        //given
        val givenBody = "{\"Size\" : \"20\"}"
        testHelper.givenGet("/hook/test")
        testHelper.givenPut("/hook/test", givenBody)

        //when
        val response = testHelper.whenPut("/hook/test", givenBody)

        //then
        val hook = hookRepository.findByName("test")

        response.statusCode shouldBe HttpStatus.SC_OK
        assert(hook.isPresent)
        hook.ifPresent {
            it.name shouldBe "test"
            it.events.size shouldBe 2
            it.events[0].request.method shouldBe "GET"
            it.events[1].request.method shouldBe "POST"
            it.events[1].request.body shouldBe givenBody

        }
    }
}