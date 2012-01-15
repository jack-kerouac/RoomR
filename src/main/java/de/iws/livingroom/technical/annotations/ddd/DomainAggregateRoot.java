package de.iws.livingroom.technical.annotations.ddd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used to mark domain entity classes as the roots of an aggregate.. See the
 * book "Domain-Driven Design" for their "Aggregate" pattern!
 * 
 * @author "Florian Rampp (Florian.Rampp@jambit.com)"
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainAggregateRoot {

}
