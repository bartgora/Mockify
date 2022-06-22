package pl.mockify.server

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
internal open class TestConfig {

    @Bean(destroyMethod = "stop")
     fun wireMockServer(@Value("\${wiremock.port}") wiremockPort: Int): WireMockServer {
        WireMock.configureFor(wiremockPort)
        return WireMockServer(wiremockPort).apply {
            this.start()
        }
    }

    companion object {

        private var wiremockInstance: WireMockServer? = null
    }
}