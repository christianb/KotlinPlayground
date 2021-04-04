package coroutine.cancelling

import coroutine.benchmark
import coroutine.logJobsChildren
import kotlinx.coroutines.*

// Does all nested jobs get cancelled when the scope gets cancelled?

fun main() = runBlocking {
    benchmark {
        val job = Job()
        val coroutineScope = CoroutineScope(Dispatchers.Default + job)

        val job1 = coroutineScope.launch {
            launch { delay(200) }
            delay(200L)
        }
        val job2 = coroutineScope.launch { delay(200L) }

        logJobsChildren(job) // all children jobs are in "Active" state
        job1.cancel() // cancel Job1
        logJobsChildren(job) // job1 and it's children are in "Cancelling" state, the others are still in "Active" state

        joinAll(job1, job2)
    }
}