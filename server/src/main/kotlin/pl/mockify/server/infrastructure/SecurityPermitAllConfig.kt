package pl.mockify.server.infrastructure

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration


@Configuration
open class SecurityPermitAllConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedHeaders = listOf("Authorization", "Cache-Control", "Content-Type")
        corsConfiguration.allowedOrigins = listOf("*")
        corsConfiguration.allowedMethods =
            listOf("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE")
        corsConfiguration.exposedHeaders = listOf("Authorization")
        http!!.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
            .authenticated().and().csrf().disable().cors()
            .configurationSource { corsConfiguration }



    }
}