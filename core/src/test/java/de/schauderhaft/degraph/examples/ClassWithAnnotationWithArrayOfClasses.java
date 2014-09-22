package de.schauderhaft.degraph.examples;

@SomeAnnotation({Number.class, Double.class, Float.class})
@AnnoA(@AnnoB(@AnnoC))
public class ClassWithAnnotationWithArrayOfClasses {
}
