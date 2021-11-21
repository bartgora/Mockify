package pl.mockify.server.data

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime


@Component
class ExpireHookCleaner(private var hookRepository: HookRepository) {

    @Scheduled(fixedDelay = 600)
    fun execute(){
        print("execute")
        val timeStamp = Timestamp.valueOf(LocalDateTime.now())
        val hooks = hookRepository.findByLastModifiedGreaterThanEqual(timeStamp)
        hookRepository.deleteAll(hooks)
    }

}