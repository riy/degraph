package de.schauderhaft.degraph.check

import de.schauderhaft.degraph.configuration.Configuration
import de.schauderhaft.degraph.configuration.Constraint

case class ConstraintBuilder(configuration: Configuration, sliceType: String = "") {
    private def any2Layer(arg: AnyRef): Layer = arg match {
        case s: String => LenientLayer(s)
        case l: Layer => l
        case _ => throw new IllegalArgumentException("Only arguments of type String or Layer are accepted")
    }

    private def modifyConfig(slices: IndexedSeq[AnyRef], toConstraint: (String, IndexedSeq[Layer]) => Constraint): ConstraintBuilder =
        copy(configuration =
            configuration.copy(
                constraint =
                    configuration.constraint +
                        toConstraint(sliceType, slices.map((x: AnyRef) => any2Layer(x)))))

    def allow(slices: AnyRef*): ConstraintBuilder =
        modifyConfig(slices.toIndexedSeq, LayeringConstraint)

    def allowDirect(slices: AnyRef*): ConstraintBuilder =
        modifyConfig(slices.toIndexedSeq, DirectLayeringConstraint)

    def withSlicing(sliceType: String, sls: AnyRef*) = copy(sliceType = sliceType)

    def including(s: String): ConstraintBuilder = copy(configuration = configuration.copy(includes = configuration.includes :+ s))

}