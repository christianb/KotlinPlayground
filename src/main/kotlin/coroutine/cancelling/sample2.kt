package coroutine.cancelling

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

// How can I make a coroutine cooperative?

fun main() = runBlocking {
    val start = System.currentTimeMillis()

    val job = CoroutineScope(Dispatchers.IO).launch {
        cooperativeTask()
    }

    job.cancel()
    job.join() // the cooperative task stops working when the coroutine is no longer in "Active" state

    val end = System.currentTimeMillis()
    println("completed after ${end - start}ms")
}

// This function is cooperative.
private suspend fun cooperativeTask() {
    val start = System.currentTimeMillis()
    val duration = 2000L

    // checks if the Coroutine is in "Active" state
    while(start + duration > System.currentTimeMillis() && coroutineContext.isActive) {
        // do nothing
    }
}

