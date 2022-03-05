package pl.mockify.server

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.patch
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.put
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response
import org.springframework.stereotype.Component


@Component
class TestHelper {

    fun givenGet(path: String) {
        stubFor(get(path).willReturn(aResponse().withStatus(200)))
    }

    fun givenPost(path: String, body: String) {
        stubFor(post(path).withRequestBody(equalToJson(body)).willReturn(aResponse().withStatus(200)))
    }

    fun givenPut(path: String, body: String) {
        stubFor(put(path).withRequestBody(equalToJson(body)).willReturn(aResponse().withStatus(200)))
    }

    fun givenPatch(path: String, body: String) {
        stubFor(patch(urlPathEqualTo(path)).withRequestBody(equalToJson(body)).willReturn(aResponse().withStatus(200)))
    }

    fun whenGet(path: String): Response {
        return RestAssured.with()
            .`when`()
            .get(path)
    }

    fun whenPost(path: String, body: String): Response {
        return RestAssured.with()
            .body(body)
            .contentType(ContentType.JSON)
            .`when`()
            .post(path)
    }

    fun whenPut(path: String, body: String): Response {
        return RestAssured.with()
            .body(body)
            .contentType(ContentType.JSON)
            .`when`()
            .put(path)
    }

    fun whenPatch(path: String, body: String): Response {
        return RestAssured.with()
            .body(body)
            .contentType(ContentType.JSON)
            .`when`()
            .patch(path)
    }
}

