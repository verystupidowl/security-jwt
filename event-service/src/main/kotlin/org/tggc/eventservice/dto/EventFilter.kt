package org.tggc.eventservice.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class EventFilter(
    val title: String? = null,
    val creatorId: Long? = null,
    @param:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val startDate: LocalDateTime? = null,
    @param:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val endDate: LocalDateTime? = null
)
