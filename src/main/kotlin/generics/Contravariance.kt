package generics

// Contravariance:
interface ContravarianceSuperType {
    fun match(subject: Dog)
}

// Contravariance: a subtype can be replaced by a super-type
interface ContravarianceSubType : ContravarianceSuperType {
    // Kotlin (as most languages) do not allow for contravariant argument types.
    // override fun match(subject: Animal) // ERROR!
}