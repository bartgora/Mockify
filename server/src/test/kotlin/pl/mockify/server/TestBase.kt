package pl.mockify.server

import com.github.tomakehurst.wiremock.client.WireMock
import io.restassured.RestAssured
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestConstructor
import org.springframework.util.SocketUtils
import pl.mockify.server.domain.facades.HookFacade


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [ServerApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
open class TestBase {

    @LocalServerPort
    private val port = 0

    @Autowired
    lateinit var hookFacade: HookFacade

    @Autowired
    lateinit var repositories: List<JpaRepository<*, *>>

    @Autowired
    lateinit var testHelper: TestHelper

    @AfterEach
    fun reset() {
        WireMock.reset()
    }

    @BeforeEach
    fun clearRepositories() {
        repositories.map { repository -> repository.deleteAllInBatch() }
    }

    @BeforeEach
    open fun setUp() {
        RestAssured.port = port
    }

    companion object {

        private val WIREMOCK_PORT = SocketUtils.findAvailableTcpPort()

        @JvmStatic
        @DynamicPropertySource
        fun wireMockProperties(registry: DynamicPropertyRegistry) {
            registry.add("wiremock.port") { WIREMOCK_PORT }
        }
    }

}
