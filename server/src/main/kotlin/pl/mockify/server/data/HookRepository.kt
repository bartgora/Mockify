package pl.mockify.server.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional
interface HookRepository : JpaRepository<Hook, Long> {

    fun findByLastModifiedBefore(lastModified: Timestamp): List<Hook>

    fun findByName(name: String): Optional<Hook>
}