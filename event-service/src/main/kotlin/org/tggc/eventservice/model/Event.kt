package org.tggc.eventservice.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "events")
open class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,
    open var title: String? = null,
    open var description: String? = null,
    open var eventDate: LocalDateTime? = null,
    open var location: String? = null,
    open var creatorId: Long? = null,
    open var createdAt: LocalDateTime? = null,
    open var updatedAt: LocalDateTime? = null,
    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var participants: MutableList<Participant> = mutableListOf()
)
