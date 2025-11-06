package org.tggc.eventservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.tggc.eventservice.model.Participant

@Repository
interface ParticipantRepository : JpaRepository<Participant?, Long?> {

    fun findByEventId(eventId: Long?): MutableList<Participant?>?

    fun existsByEventIdAndUserId(eventId: Long, userId: Long): Boolean
}
