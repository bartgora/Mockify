package pl.mockify.server.data

import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
open class ExpireHookCleaner(private var hookRepository: HookRepository) {

    private var logger = KotlinLogging.logger("ExpireHookCleaner")

    @Scheduled(fixedDelay = 7200000)
    @Transactional
    open fun execute() {
        logger.info { "Executing Scheduler" }
        val timeStamp = Timestamp.valueOf(LocalDateTime.now().minusHours(8))
        val hooks = hookRepository.findByLastModifiedBefore(timeStamp)
        logger.info { "cleaning ${hooks.size} hooks" }
        hookRepository.deleteAll(hooks)
    }
}