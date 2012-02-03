package models.annotations.ddd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used to mark classes responsible for retrieving
 * existing domain objects and store new ones. They should be usable like a
 * in-memory collection of domain objects. See the book "Domain-Driven Design"
 * for their "Repository" pattern!
 * 
 * @author "Florian Rampp (Florian.Rampp@jambit.com)"
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainRepository {

}
