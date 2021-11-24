package pl.mockify.server.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import javax.transaction.Transactional

@Repository
@Transactional
interface HookRepository : CrudRepository<Hook, Long> {

    fun findByLastModifiedBefore(lastModified: Timestamp): List<Hook>

    fun getByName(name: String): Hook?
}