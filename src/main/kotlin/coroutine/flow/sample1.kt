package coroutine.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

// Can I emit a value from a different context?

fun main() = runBlocking {
    // wrong().collect { println(it) }
    correct().collect {
        println(it)
    }
}

// You can not emit a value from a different context!
fun wrong(): Flow<Int> = flow {
    // The WRONG way to change context for CPU-consuming code in flow builder
    withContext(Dispatchers.Default) {
        for (i in 1..3) {
            Thread.sleep(100) // pretend we are computing it in CPU-consuming way
            emit(i) // emit next value
        }
    }
}

fun correct(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(500) // pretend we are computing it in CPU-consuming way
        emit(i) // emit next value
    }
}.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder