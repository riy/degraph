package de.schauderhaft.degraph.jdk8tests;

import java.sql.SQLException;

public class ClassWithTypeAnnotations //    implements clause:
        implements @TypeAnno1 List<@TypeAnno2 T> {


    //    Class instance creation expression:
    private Object x = new @TypeAnno3 Object();

    private Object str = "Hallo";

    //    Type cast:
    private String myString = (@TypeAnno4 String) str;

    //    Thrown exception declaration:
    void throwsAnnotatedException() throws @TypeAnno5 SQLException {
    }
}
