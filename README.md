# Kotlin with Samples

This repositories shows some samples in Kotlin.

## Coroutines
* [Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)
* [Coroutines Best Practices](https://developer.android.com/kotlin/coroutines/coroutines-best-practices)

## Dispatchers
* [Coroutines Dispatchers.Default and Dispatchers.IO Considered Harmful](https://www.techyourchance.com/coroutines-dispatchers-default-and-dispatchers-io-considered-harmful/)

### Dispatchers.Default

The documentation for [Dispatchers.Default](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-default.html) 
says: _"is backed by a shared pool of threads on JVM. By default, the maximum number of threads used by this dispatcher is equal to the number of CPU cores, but is at least two."_
It does not tell about any usecase when to use `Dispatchers.Default`!

Why you should avoid Dispatchers.Default:
1. Using a single bounded thread pool to execute many unrelated flows presents a risk of performance and liveness problems. The magnitude of this risk is inversely proportional to the number of CPU cores, which means that users with “weaker” devices are more exposed to it.
2. There is no need to optimize for throughput in absolute majority of the cases (even if you do need to execute CPU-bound tasks), so that’s just premature optimization.
3. This dispatcher is poorly named and poorly documented (compared to, for example, RxJava’s Schedulers.computation()).

### Dispatchers.IO

The documentation for [Dispatchers.IO](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-i-o.html) 
says: _"It defaults to the limit of 64 threads or the number of cores (whichever is larger)."_
It does not tell why this Dispatcher is limited to specifically 64 threads.

### Custom Unbound Dispatcher
As an alternative to `Dispatchers.Default` and `Dispatchers.IO` you can use a  single unbounded [Dispatchers.Background](https://github.com/techyourchance/android-coroutines-course/blob/master/app/src/main/java/com/techyourchance/coroutines/demonstrations/backgrounddispatcher/BackgroundDispatcher.kt) for all background work (regardless of whether this work is CPU-bound, IO-bound, or otherwise). 
If you see real performance issues, introduce additional dispatchers with different configuration for specific “types of tasks”. 
In practice, absolute majority of Android apps won’t ever need additional dispatchers. 