package generics

interface Group<T>: ReadableGroup<T>, WritableGroup<T>

interface ReadableGroup<out T> {
    fun fetch(): T?
}

interface WritableGroup<in T> {
    fun insert(item: T)
}

class GroupImpl<T> : Group<T> {

    private var t: T? = null

    override fun fetch(): T? = t

    override fun insert(item: T) {
       t = item
    }
}