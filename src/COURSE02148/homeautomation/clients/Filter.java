package COURSE02148.homeautomation.clients;

import COURSE02148.homeautomation.server.api.Intent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Filter {
    Intent filterKey();
    String filterValue();
}
