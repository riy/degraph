package de.schauderhaft.degraph.check

import de.schauderhaft.degraph.configuration.Configuration
import de.schauderhaft.degraph.configuration.Constraint
import de.schauderhaft.degraph.configuration.Pattern
import de.schauderhaft.degraph.configuration.UnnamedPattern
import de.schauderhaft.degraph.configuration.NamedPattern

case class ConstraintBuilder(private val config: Configuration = new Configuration(), sliceType: String = "", includes: Seq[String] = Seq(), slicings: Map[String, Seq[Pattern]] = Map()) {
    private def any2Layer(arg: AnyRef): Layer = arg match {
        case s: String => LenientLayer(s)
        case l: Layer => l
        case _ => throw new IllegalArgumentException("Only arguments of type String or Layer are accepted")
    }

    private def modifyConfig(slices: IndexedSeq[AnyRef], toConstraint: (String, IndexedSeq[Layer]) => Constraint): ConstraintBuilder =
        copy(config =
            config.copy(
                constraint =
                    config.constraint +
                        toConstraint(sliceType, slices.map((x: AnyRef) => any2Layer(x)))))

    def allow(slices: AnyRef*): ConstraintBuilder =
        modifyConfig(slices.toIndexedSeq, LayeringConstraint)

    def allowDirect(slices: AnyRef*): ConstraintBuilder =
        modifyConfig(slices.toIndexedSeq, DirectLayeringConstraint)

    def withSlicing(sliceType: String, sls: AnyRef*) = {
        copy(sliceType = sliceType, slicings = slicings + (sliceType ->
            sls.map {
                case s: String => UnnamedPattern(s)
                case (n: String, p: String) => NamedPattern(n, p)
            }))
    }

    def including(s: String): ConstraintBuilder = copy(includes = includes :+ s)

    def configuration = config.copy(categories = slicings, includes = includes)

}