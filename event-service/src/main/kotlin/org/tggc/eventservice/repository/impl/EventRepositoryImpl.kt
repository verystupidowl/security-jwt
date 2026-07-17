package org.tggc.eventservice.repository.impl

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import org.tggc.eventservice.dto.EventFilter
import org.tggc.eventservice.model.Event
import org.tggc.eventservice.repository.EventRepositorySpec
import reactor.core.publisher.Flux
import java.util.Locale.getDefault

@Repository
class EventRepositoryImpl(private val template: R2dbcEntityTemplate) : EventRepositorySpec {
    override fun findAll(filter: EventFilter): Flux<Event> {
        var criteria = Criteria.empty()

        filter.title?.let {
            criteria = criteria.and("title")
                .like("%${it.lowercase(getDefault())}%")
        }

        filter.creatorId?.let {
            criteria = criteria.and("creatorId")
                .`is`(it)
        }

        filter.startDate?.let {
            criteria = criteria.and("startDate")
                .greaterThanOrEquals(it)
        }

        filter.endDate?.let {
            criteria = criteria.and("endDate")
                .lessThan(it)
        }

        return template.select(
            Query.query(criteria),
            Event::class.java
        )
    }

}