package pl.mockify.server.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<Event, Long> {

    @Query("DELETE FROM Event e where e.hook =?1")
    fun deleteAllEventsByHook(hook: Hook)
}