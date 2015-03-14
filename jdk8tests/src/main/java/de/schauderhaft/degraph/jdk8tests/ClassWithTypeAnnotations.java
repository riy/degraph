package de.schauderhaft.degraph.jdk8tests;

import java.sql.SQLException;
import java.util.List;

public class ClassWithTypeAnnotations<T> //    implements clause:
        implements @TypeAnno1 ClassWithTypeParam<@TypeAnno2 T> {


    //    Class instance creation expression:
    private Object x = new @TypeAnno3 Object();

    private Object str = "Hallo";

    //    Type cast:
    private String myString = (@TypeAnno4 String) str;

    //    Thrown exception declaration:
    void throwsAnnotatedException() throws @TypeAnno5 SQLException {
    }

    @Override
    public @TypeAnno2 T doSomething() {
        return null;
    }
}
