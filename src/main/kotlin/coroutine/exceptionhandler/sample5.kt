package coroutine.exceptionhandler

import coroutine.logJobsChildren
import coroutine.threadInfo
import kotlinx.coroutines.*
import java.lang.RuntimeException

// Can I try-catch inside a coroutine? Will it still go to the CoroutineExceptionHandler?

fun main() = runBlocking {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("${threadInfo()}, uncaught exception $exception, ${coroutineContext[Job]}") // this gets never called
        // as the exception was caught in the try-catch
    }

    val coroutineScope = CoroutineScope(Dispatchers.Default + exceptionHandler)

    coroutineScope.launch {
        try {
            throw RuntimeException()
        } catch (e: Exception) {
            // a try catch inside launched coroutine will catch it
            println("${threadInfo()}, caught exception $e")
            println(coroutineContext[Job]) // The Coroutine's Job is still active
        }
    }.join()
}

