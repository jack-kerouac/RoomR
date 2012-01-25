package de.iws.mucflatshares.technical.annotations.ddd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used to mark classes that follow the DDD "Service"
 * pattern. See the book "Domain-Driven Design" for their "Service" pattern!
 * 
 * @author "Florian Rampp (Florian.Rampp@jambit.com)"
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainService {

}
