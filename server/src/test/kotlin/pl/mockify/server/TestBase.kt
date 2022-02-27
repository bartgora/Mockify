package pl.mockify.server

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.CrudRepository
import org.springframework.test.context.ActiveProfiles
import pl.mockify.server.data.HookRepository
import pl.mockify.server.domain.facades.HookFacade

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
open class TestBase {

    @Autowired
    lateinit var hookRepository: HookRepository

    @Autowired
    lateinit var hookFacade: HookFacade

    @Autowired
    lateinit var repositories: List<CrudRepository<*, *>>

    @Autowired
    lateinit var testHelper: TestHelper

    @BeforeAll
    fun reset() {

    }

    @AfterEach
    fun clearRepositories() {
        repositories.map { crudRepository -> crudRepository.deleteAll() }
    }


}