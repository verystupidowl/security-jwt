package org.tggc.eventservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import org.tggc.eventservice.model.Event
import java.time.LocalDateTime

@Repository
interface EventRepository : JpaRepository<Event?, Long?>, JpaSpecificationExecutor<Event> {
    fun findByCreatorId(userId: Long?): MutableList<Event>

    fun findByEventDateBetween(now: LocalDateTime?, soon: LocalDateTime?): MutableList<Event>
}
