package pl.mockify.server.data

import org.springframework.data.repository.CrudRepository


interface HookRepository : CrudRepository<Hook, Long> {
}