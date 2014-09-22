package de.schauderhaft.degraph.examples;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface SomeAnnotation {
    public Class[] value();
}
