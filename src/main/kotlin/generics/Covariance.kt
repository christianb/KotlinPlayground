package generics

// Covariance:
interface CovarianceSuperType {
    fun match(): Mammal
}

// A subtype must return at most the same range of types as its supertype declares.
// So it returns the same type of a subtype of it.
interface CovarianceSubType : CovarianceSuperType {
    // Covariant return types allow methods on a subclass to return a more specific type than their superclass.
    override fun match(): Dog // a Dog is a Mammal
}