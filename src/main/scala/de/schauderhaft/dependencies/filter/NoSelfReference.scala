package de.schauderhaft.dependencies.filter

object NoSelfReference extends (((AnyRef, AnyRef)) => Boolean) {
    def apply(t : (AnyRef, AnyRef)) : Boolean = t._1 != t._2
}