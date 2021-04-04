package coroutine.cancelling

import coroutine.benchmark
import kotlinx.coroutines.*

// Is a Coroutine cancelled "automatically"?

fun main() = runBlocking {
    benchmark {
        val job = CoroutineScope(Dispatchers.Default).launch {
            nonCooperativeTask()
        }

        job.cancel() // though the job gets canceled
        job.join() // nonCooperativeTask() will not stop its execution
    }
}

// This function is not cooperative.
// It does not respect the cancellation state of the coroutine it runs in.
private fun nonCooperativeTask() {
    val start = System.currentTimeMillis()
    val duration = 2000L

    while (start + duration > System.currentTimeMillis()) {
        // do nothing
    }
}

