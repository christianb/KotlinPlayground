package generics

/**
 * https://typealias.com/guides/illustrated-guide-covariance-contravariance/
 * https://typealias.com/guides/ins-and-outs-of-generic-variance/
 */

fun main(args: Array<String>) {
    val mammalGroup: Group<Mammal> = GroupImpl()

    mammalGroup.insert(Dog())
    mammalGroup.insert(Mammal())
    // mammalGroup.insert(Animal()) // ERROR: allowed to pass same or sub-types, but Animal is a super-type of Mammal

    val mammal: Mammal? = mammalGroup.fetch()
    val animal: Animal? = mammalGroup.fetch()
    // val dog: Dog? = mammalGroup.fetch() // ERROR: allowed to return same or super-types, but Dog is a sub-type of Mammal
}