package pl.mockify.server

import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test


class HookFacadeFunctionalTest() : TestBase() {

    @Test
    fun `should Add new Hook`() {

        //when
        testHelper.whenGet("test")

        //then

        val hook = hookRepository.findByName("test")

        hook shouldNotBe hook.isEmpty
    }
}