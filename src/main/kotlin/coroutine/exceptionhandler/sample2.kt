package coroutine.exceptionhandler

import coroutine.logJobsChildren
import coroutine.threadInfo
import kotlinx.coroutines.*
import java.lang.RuntimeException

// Which effect has an exception to other coroutines within the same CoroutineScope?

fun main() = runBlocking {
    val job = Job()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("${threadInfo()}, uncaught exception $exception, Job: ${coroutineContext[Job]}")
        logJobsChildren(job) // no other jobs are active
    }

    val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler + job)

    val job1 = coroutineScope.launch {
        delay(50L)
        throw RuntimeException()
    }

    val job2 = coroutineScope.launch {
        delay(200L)
        println("job2 done") // This job gets never done when an uncaught exception happens anywhere in the CoroutineScope!
    }

    joinAll(job1, job2)
}

