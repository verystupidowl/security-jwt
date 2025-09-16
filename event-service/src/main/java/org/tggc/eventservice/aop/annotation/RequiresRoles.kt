package org.tggc.eventservice.aop.annotation

import org.tggc.eventservice.aop.Role

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CLASS
)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiresRoles(vararg val value: Role)
