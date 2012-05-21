package de.schauderhaft.dependencies.categorizer
import scala.util.matching.Regex
import com.jeantessier.dependency.ClassNode

object PackageCategorizer extends Function1[AnyRef, AnyRef] {
    def apply(value : AnyRef) = {
        value match {
            case cn : ClassNode => cn.getPackageNode()
            case _              => value
        }
    }
}

object PackageRegexpCategorizer {
    def pattern(pattern : Regex) : AnyRef => AnyRef = {
        x =>
            x match {
                case cn : ClassNode => {
                    val name = cn.getPackageNode().getName()
                    val singleMatch = pattern.unapplySeq(name)
                    if (singleMatch.size == 1)
                        singleMatch.head.head
                    else cn
                }
                case _ => x
            }
    }
}

object InternalClassCategorizer extends Function1[AnyRef, AnyRef] {
    override def apply(any : AnyRef) : AnyRef = {
        any match {
            case cn : ClassNode if (cn.getName().contains("$")) =>
                new ClassNode(cn.getPackageNode(), cn.getName().split("""\$""")(0), true)
            case _ => any
        }
    }
}

object MultiCategorizer {
    def combine(categorizer : (AnyRef => AnyRef)*) : AnyRef => AnyRef = {
        x : AnyRef =>
            x match {
                case Seq(head)            => Seq(head)
                case Seq(head, rest @ _*) => rest
                case _                    => categorizer.map(_(x))
            }
    }
}