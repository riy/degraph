package de.schauderhaft.degraph.examples;

import scala.annotation.meta.field;

@SuppressWarnings("ALL")
public class InstanceReferenceToStaticField {
    public String field;
    public void doSomething(){
        field = StaticMethod.field;
    }
}
