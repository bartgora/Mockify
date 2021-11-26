package pl.mockify.server.data

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
interface HookRepository : CrudRepository<Hook, Long> {

    fun findByLastModifiedBefore(lastModified: Timestamp): List<Hook>

    fun getByName(name: String): Hook?
}