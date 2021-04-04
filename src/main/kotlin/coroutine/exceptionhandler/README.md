### On which thread the CoroutineExceptionHandler will catch the Exception?
The thread a `CoroutineExceptionHandler` is called on is the same thread the coroutine had run when the exception was thrown.
```kotlin
val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    println("${Thread.currentThread().name}, uncaught exception $exception")
    // called on the same thread where the exception is thrown
}

val job1 = CoroutineScope(Dispatchers.Unconfined + exceptionHandler).launch {
    throw RuntimeException()
}

val job2 = CoroutineScope(Dispatchers.Default + exceptionHandler).launch {
    throw RuntimeException()
}
```
So it is not a good idea to perform any UI operation inside `CoroutineExceptionHandler`. To handle exceptions properly, try-catch them manually, and return an error result instead.

### Which effect has an exception to other coroutines within the same CoroutineScope?
Any Coroutine that throws an exception that is not caught will let all other coroutines within the same job and its children fail.
```kotlin
val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
    println("${Thread.currentThread().name()}, uncaught exception $exception")
}

val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)

val job1 = coroutineScope.launch {
    throw RuntimeException()
}

val job2 = coroutineScope.launch {
    delay(50L)
    println("job2 done") // This job gets never done when an uncaught exception happens anywhere in the CoroutineScope!
}

```

### How to make other Coroutine Jobs (in the same CoroutineScope) keep running when an unhandled exception happens in the same CoroutineScope?
You can pass a [SupervisorJob](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-supervisor-job.html). 
It allows children to fail independently of each other.
```kotlin
val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
    println("${Thread.currentThread().name()}, uncaught exception $exception")
}

val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler + SupervisorScope())

val job1 = coroutineScope.launch {
    throw RuntimeException()
}

val job2 = coroutineScope.launch {
    delay(50L)
    println("job2 done") // within a SupervisorJob this job can finish when other jobs are failing
}

```

### Can I try-catch a Coroutine launch() call?
A try catch around `launch` will never catch an exception inside a that Coroutine. The exception will go to the (
default) CoroutineExceptionHandler and may other jobs fail if they are not running within a `SupervisorJob`.
```kotlin
 val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
    println("${threadInfo()}, uncaught exception $exception, ${coroutineContext[Job]}")
    // will receive the RuntimeException
}

val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)

try {
    coroutineScope.launch {
        throw RuntimeException()
    }.join()
} catch (e: Exception) {
    println("caught exception: $e") // this gets never called
}
```

### Can I try-catch inside a coroutine? Will it still go to the CoroutineExceptionHandler?
Doing a `try-catch` inside launch will catch exceptions from the coroutine suspend functions.
```kotlin
val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
    // does not get called
}

val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)

coroutineScope.launch {
    try {
        throw RuntimeException()
    } catch (e: Exception) {
        // catch the exception
        println("${Thread.currentThread().name}, caught exception $e")
        println(coroutineContext[Job]) // The Coroutine's Job is still active
    }
}.join()
```