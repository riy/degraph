package de.schauderhaft.degraph.categorizer
import com.jeantessier.dependency.ClassNode
import de.schauderhaft.degraph.analysis.Node

object PackageCategorizer extends Function1[AnyRef, AnyRef] {
    def apply(value: AnyRef) = {
        value match {
            case Node(t, n) if (t == "Class") => Node("Package", packagePart(n))
            case _ => value
        }
    }

    def packagePart(name: String) = {
        val lastDotIndex = name.lastIndexOf('.')
        if (lastDotIndex >= 0)
            name.substring(0, lastDotIndex)
        else
            name
    }
}
