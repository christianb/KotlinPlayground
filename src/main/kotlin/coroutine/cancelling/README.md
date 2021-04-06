### When cancelling a coroutine, what happens to the execution of the function(s) inside that coroutine?
Every coroutine must be cooperative when it receives the cancellation signal.
A non-cooperative coroutine will run until its function ends or the process is killed.
```kotlin
/**
 * This function is not cooperative.
 * It does not respect the state of the coroutine it runs in.
 */
private fun nonCooperativeTask() {
    // very long operation
}

val job = CoroutineScope(Dispatchers.IO).launch { 
    nonCooperativeTask()
}

job.cancel()
```
After `job.cancel()`, `nonCooperativeTask()` is still running because it does not check the state of its coroutine.

### How can I make a suspend function cooperative?
To make a function cooperative, you need to check the state of its coroutine either by:
* [coroutineContext.isActive](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/kotlin.coroutines.-coroutine-context/is-active.html)
* or [coroutineContext.ensureActive](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/ensure-active.html)
```kotlin
/**
 * This function is cooperative.
 * It does not respect the state of the coroutine it runs in.
 */
private fun cooperativeTask() {
    while (`coroutine.isActive`) {
        // do something
    }
}

val job = CoroutineScope(Dispatchers.IO).launch { 
    cooperativeTask()
}

job.cancel()
```
After `job.cancel()`, `cooperativeTask()` can still run. At the next check of `coroutineContext.isActive` it will exit its loop.

### Does all jobs get cancelled when the scope gets cancelled?
All of a Jobs children Jobs gets cancelled when the parent job receives a cancellation Signal.
```kotlin
val job = Job() 
val coroutineScope = CoroutineScope(Dispatchers.IO + job)

val childJob1 = coroutineScope.launch { delay(500L) }
val job2 = coroutineScope.launch { delay(500L) }
// all children jobs are in "Active" state

coroutineScope.cancel() // or job.cancel()
// all children jobs are in "Cancelling" state
```