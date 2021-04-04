package coroutine

import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    launch {
        println(foo())
    }.join()
}

suspend fun foo(): String = withContext(Dispatchers.IO) {
    delay(1000L)
    "Foo"
}