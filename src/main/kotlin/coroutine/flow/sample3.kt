package coroutine.flow

import coroutine.benchmark
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

// Can I make the flow run concurrently with the Collector?

fun main() = runBlocking {
    benchmark {
        myFlow()
            .buffer() // Using a buffer creates a channel. The same effect you get using flowOn()
            .collect {
            delay(300) // this collector needs 100 ms to do some work before it can process the next value
            println(it)
        }
    }
}


private fun myFlow(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(300) // every emitted item needs 300 ms
        emit(i)
    }
}