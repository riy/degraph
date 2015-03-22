package de.schauderhaft.degraph.jdk8tests;

import java.sql.SQLException;

public class ClassWithTypeAnnotations<T> //    implements clause:
        implements @TypeAnno1 ClassWithTypeParam<@TypeAnno2 T> {


    //    Class instance creation expression:
    private Object x = new @TypeAnno3 Object();

    private @TypeAnno6 Object str = "Hallo";

    //    Type cast:
    public String myString = (@TypeAnno4 String) str;

    //    Thrown exception declaration:
    public void throwsAnnotatedException() throws @TypeAnno5 SQLException {
    }

    @Override
    public @TypeAnno2 T doSomething() {
        try{
            @TypeAnno8 String local = new String();

        } catch (@TypeAnno7 RuntimeException re){

        }

        return null;
    }
}
