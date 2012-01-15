package de.iws.livingroom.technical.annotations.ddd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used to mark classes or methods responsible for initially creating domain
 * objects. See the book "Domain-Driven Design" for their "Factory" pattern!
 * 
 * @author "Florian Rampp (Florian.Rampp@jambit.com)"
 * 
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainFactory {

}
