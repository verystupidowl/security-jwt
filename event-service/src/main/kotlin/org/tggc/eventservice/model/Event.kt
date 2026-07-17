package org.tggc.eventservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "events")
open class Event(
    @Id
    open var id: Long? = null,
    open var title: String? = null,
    open var description: String? = null,
    open var eventDate: LocalDateTime? = null,
    open var location: String? = null,
    open var creatorId: Long? = null,
    open var createdAt: LocalDateTime? = null,
    open var updatedAt: LocalDateTime? = null
)
