package pl.mockify.server

import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.CrudRepository
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.util.SocketUtils
import pl.mockify.server.data.HookRepository

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [ServerApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
open class TestBase {


    @Autowired
    lateinit var hookRepository: HookRepository


    @Autowired
    lateinit var repositories: List<CrudRepository<*, *>>

    @Autowired
    lateinit var testHelper: TestHelper

    @AfterEach
    fun reset() {
        WireMock.reset()
    }

    @BeforeEach
    fun clearRepositories() {
        repositories.map { crudRepository -> crudRepository.deleteAll() }
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
