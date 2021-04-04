package coroutine.exceptionhandler

import coroutine.threadInfo
import kotlinx.coroutines.*
import java.lang.RuntimeException

// Can I try-catch a Coroutine launch() call?

fun main() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("${threadInfo()}, uncaught exception $exception, ${coroutineContext[Job]}")
    }

    val coroutineScope = CoroutineScope(Dispatchers.IO + exceptionHandler)

    try {
        coroutineScope.launch {
            throw RuntimeException()
        }.join()
    } catch (e: Exception) {
        // a try catch around launch coroutine will never catch an exception inside a coroutine
        // it still goes to the (default) CoroutineExceptionHandler
        println("caught exception: $e") // this gets never called
    }
}

