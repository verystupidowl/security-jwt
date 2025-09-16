package org.tggc.eventservice.dto

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Builder
import java.time.LocalDateTime

@Builder
data class EventRq(
    val title: String?,
    val description: String?,
    @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") @param:JsonFormat(
        pattern = "yyyy-MM-dd'T'HH:mm"
    ) val eventDate: LocalDateTime?,
    val location: String?
)
