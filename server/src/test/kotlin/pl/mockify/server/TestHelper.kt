package pl.mockify.server

import io.restassured.RestAssured
import org.springframework.stereotype.Component


@Component
class TestHelper {

    fun whenGet(hookName: String) {
        RestAssured.with()
            .param("hookName", hookName)
            .`when`()
            .get("/hook/$hookName")

    }
}