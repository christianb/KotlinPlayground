package coroutine.cancelling

import coroutine.logJobsChildren
import kotlinx.coroutines.*

// Does all jobs get cancelled when the scope gets cancelled?

fun main() = runBlocking {
    val start = System.currentTimeMillis()

    val job = Job()
    val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    val job1 = coroutineScope.launch {
        delay(500L) // any built in suspend function like delay is cooperative!
    }

    val job2 = coroutineScope.launch { delay(500L) }
    val job3 = coroutineScope.launch { delay(500L) }

    logJobsChildren(job) // all children jobs are in "Active" state
    coroutineScope.cancel()
    logJobsChildren(job) // all children jobs are in "Cancelling" state

    joinAll(job1, job2, job3)

    val end = System.currentTimeMillis()
    println("completed after ${end - start}ms")
}
