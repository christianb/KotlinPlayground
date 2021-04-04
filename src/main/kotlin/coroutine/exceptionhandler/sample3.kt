package coroutine.exceptionhandler

import coroutine.logJobsChildren
import coroutine.threadInfo
import kotlinx.coroutines.*
import java.lang.RuntimeException

// How to make other Coroutine Jobs (in the same CoroutineScope) keep running when an unhandled exception happens in the same CoroutineScope?

fun main() = runBlocking {
    val supervisorJob = SupervisorJob()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("${threadInfo()}, uncaught exception $exception, ${coroutineContext[Job]}")
        logJobsChildren(supervisorJob) // Job2 is not cancelled when Job1 fails
    }

    val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler + supervisorJob)

    val job1 = coroutineScope.launch(CoroutineName("Job1")) {
        throw RuntimeException()
    }

    val job2 = coroutineScope.launch(CoroutineName("Job2")) {
        delay(100L)
        println("Job2 done") // SupervisorJob ensures other children coroutines can keep running when an uncaught exception happens anywhere in the CoroutineScope
    }

    joinAll(job1, job2)
}

