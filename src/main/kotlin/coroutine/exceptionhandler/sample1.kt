package coroutine.exceptionhandler

import coroutine.threadInfo
import kotlinx.coroutines.*
import java.lang.RuntimeException

// On which thread the CoroutineExceptionHandler will catch the Exception?

fun main() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        // Handles exception on the specific thread the coroutine crashed in.
        println("${threadInfo()}, uncaught exception $exception")
        // So it is not a good idea to perform any UI operation here.
        // The state of the Job is
    }

    val job1 = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
        throw RuntimeException()
    }

    val job2 = CoroutineScope(Dispatchers.Unconfined + exceptionHandler).launch {
        throw RuntimeException()
    }

    joinAll(job1, job2)
}

