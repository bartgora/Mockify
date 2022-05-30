package pl.mockify.server.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface HookRepository : JpaRepository<Hook, Long> {


    fun findByName(name: String): Hook?
}