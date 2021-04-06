package coroutine.flow

import coroutine.benchmark
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

// In case the collector process items slower than the flow, can I skip some values and just get the latest one?

fun main() = runBlocking {
    benchmark {
        myFlow()
            .conflate() // conflate() can be used to skip intermediate values when a collector is too slow to process them.
            .collect {
                delay(300)
                println(it)
            }
    }
}


private fun myFlow(): Flow<Int> = flow {
    for (i in 1..7) {
        delay(100)
        emit(i)
    }
}