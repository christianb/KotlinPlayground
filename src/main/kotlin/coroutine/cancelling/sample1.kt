package coroutine.cancelling

import kotlinx.coroutines.*

// Is a Coroutine cancelled "automatically"?

fun main() = runBlocking {
    val start = System.currentTimeMillis()

    val job = CoroutineScope(Dispatchers.IO).launch {
        nonCooperativeTask()
    }

    job.cancel() // though the job gets canceled
    job.join() // it will take the the full time to complete

    val end = System.currentTimeMillis()
    println("completed after ${end - start}ms")
}

// This function is not cooperative.
// It does not respect the cancellation state of the coroutine it runs in.
fun nonCooperativeTask() {
    val start = System.currentTimeMillis()
    val duration = 2000L

    while(start + duration > System.currentTimeMillis()) {
        // do nothing
    }
}

