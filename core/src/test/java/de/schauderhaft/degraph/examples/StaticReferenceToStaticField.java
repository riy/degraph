package de.schauderhaft.degraph.examples;


@SuppressWarnings("ALL")
public class StaticReferenceToStaticField {
    public static String field ;
    public static void doSomething(){
        field = StaticMethod.field;
    }
}
