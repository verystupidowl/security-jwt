package org.tggc.eventservice.dto

import lombok.Builder
import java.time.LocalDateTime

@Builder
data class EventRs(
    val id: Long?,
    val title: String?,
    val description: String?,
    val eventDate: LocalDateTime?,
    val location: String?,
    val createdAt: LocalDateTime?,
    val participants: MutableList<String?>?
)
