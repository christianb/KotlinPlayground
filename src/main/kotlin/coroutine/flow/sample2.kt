package coroutine.flow

import coroutine.benchmark
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

// What happens if the Collector process slower as the flow that emits new values?

fun main() = runBlocking {
    benchmark {
        myFlow().collect {
            delay(300) // this collector needs 100 ms to do some work before it can process the next value
            println(it)
        }
    }
}

// Instead of 900 ms, the collector needs around 1200 ms ( 3*300 + 3*300 ).

private fun myFlow(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(300) // every emitted item needs 300 ms
        emit(i)
    }
}