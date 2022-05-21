package pl.mockify.server

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test
import pl.mockify.server.data.HookRepository


class FunctionalTest(private val hookRepository: HookRepository) : TestBase() {

    @Test
    fun `should add new Hook and one GET event to hook`() {

        //given
        testHelper.givenGet("/hook/test")

        //when
        val response = testHelper.whenGet("/hook/test")

        //then
        val hook = hookRepository.findByName("test")

        response.statusCode shouldBe HttpStatus.SC_OK
        hook shouldNotBe null
        val event = hook?.events?.get(0)
        if (event != null) {
            event.request.method shouldBe "GET"
        }

    }

    @Test
    fun `should add 2 events to hook`() {

        //given
        testHelper.givenGet("/hook/test")

        //when
        val response1 = testHelper.whenGet("/hook/test")
        val response2 = testHelper.whenGet("/hook/test")

        //then
        val hook = hookRepository.findByName("test")

        response1.statusCode shouldBe HttpStatus.SC_OK
        response2.statusCode shouldBe HttpStatus.SC_OK

        val event = hook?.events?.get(0)
        if (event != null) {
            event.request.method shouldBe "GET"
        }
        val event2 = hook?.events?.get(1)
        if (event2 != null) {
            event2.request.method shouldBe "GET"
        }

    }

    @Test
    fun `should add POST event to hook`() {

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

        val event = hook?.events?.get(0)
        if (event != null) {
            event.request.method shouldBe "GET"
        }
        val event2 = hook?.events?.get(1)
        if (event2 != null) {
            event2.request.method shouldBe "POST"
        }

    }

    @Test
    fun `should add PUT event to hook`() {

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

        val event = hook?.events?.get(0)
        if (event != null) {
            event.request.method shouldBe "GET"
        }
        val event2 = hook?.events?.get(1)
        if (event2 != null) {
            event2.request.method shouldBe "PUT"
        }

    }

    @Test
    fun `should add PATCH event to hook`() {

        //given
        val givenBody = "{\"Size\" : \"20\"}"
        testHelper.givenGet("/hook/test")
        testHelper.givenPatch("/hook/test", givenBody)

        //when
        testHelper.whenGet("/hook/test")
        val response = testHelper.whenPatch("/hook/test", givenBody)

        //then
        val hook = hookRepository.findByName("test")

        response.statusCode shouldBe HttpStatus.SC_OK

        val event = hook?.events?.get(0)
        if (event != null) {
            event.request.method shouldBe "GET"
        }
        val event2 = hook?.events?.get(1)
        if (event2 != null) {
            event2.request.method shouldBe "PATCH"
        }

    }

    @Test
    fun `should add DELETE event to hook`() {

        //given
        testHelper.givenGet("/hook/test")
        testHelper.givenDelete("/hook/test")

        //when
        testHelper.whenGet("/hook/test")
        val response = testHelper.whenDelete("/hook/test")

        //then
        val hook = hookRepository.findByName("test")

        response.statusCode shouldBe HttpStatus.SC_OK
        val event = hook?.events?.get(0)
        if (event != null) {
            event.request.method shouldBe "GET"
        }
        val event2 = hook?.events?.get(1)
        if (event2 != null) {
            event2.request.method shouldBe "DELETE"
        }

    }
}