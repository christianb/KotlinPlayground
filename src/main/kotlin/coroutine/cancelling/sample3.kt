package coroutine.cancelling

import coroutine.benchmark
import coroutine.logJobsChildren
import kotlinx.coroutines.*

// Does all jobs get cancelled when the scope gets cancelled?

fun main() = runBlocking {
    benchmark {
        val job = Job()
        val coroutineScope = CoroutineScope(Dispatchers.IO + job)

        val childJob1 = coroutineScope.launch { delay(500L) }
        val childJob2 = coroutineScope.launch { delay(500L) }

        logJobsChildren(job) // all children jobs are in "Active" state
        coroutineScope.cancel()
        logJobsChildren(job) // all children jobs are in "Cancelling" state

        joinAll(childJob1, childJob2)
    }
}
