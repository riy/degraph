package de.schauderhaft.degraph.categorizer
import scala.util.matching.Regex
import com.jeantessier.dependency.ClassNode
/**
 * categorizes classes with their respective packages
 */
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
