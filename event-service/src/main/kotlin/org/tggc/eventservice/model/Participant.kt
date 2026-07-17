package org.tggc.eventservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "participants")
open class Participant {
    @Id
    open var id: Long? = null
    open var eventId: Long? = null
    open var userId: Long? = null
    open var status: EventStatus? = null
    open var joinedAd: LocalDateTime? = null
}