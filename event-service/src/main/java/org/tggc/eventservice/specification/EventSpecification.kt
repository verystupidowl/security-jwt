package org.tggc.eventservice.specification

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import lombok.experimental.UtilityClass
import org.springframework.data.jpa.domain.Specification
import org.tggc.eventservice.model.Event
import java.time.LocalDateTime
import java.util.*

@UtilityClass
object EventSpecification {
    fun titleContains(title: String?): Specification<Event?> {
        return Specification { root: Root<Event?>?, _: CriteriaQuery<*>?, cb: CriteriaBuilder? ->
            if (title == null) null else cb!!.like(
                cb.lower(root!!["title"]),
                "%" + title.lowercase(Locale.getDefault()) + "%"
            )
        }
    }

    fun startDateAfter(date: LocalDateTime?): Specification<Event?> =
        Specification { root, _, cb -> date.let { cb.greaterThanOrEqualTo(root["startDate"], it) } }

    fun endDateBefore(date: LocalDateTime?): Specification<Event?> =
        Specification { root, _, cb -> date?.let { cb.lessThan(root["endDate"], it) } }


    fun createdBy(creatorId: Long?): Specification<Event> =
        Specification { root, _, cb ->
            creatorId?.let {
                cb.equal(root.get<Long>("creatorId"), it)
            }
        }
}
