package pl.mockify.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
open class ServerApplication

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}
