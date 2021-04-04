package coroutine.cancelling

import coroutine.benchmark
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

// How can I make a coroutine cooperative?

fun main() = runBlocking {
    benchmark {
        val job = CoroutineScope(Dispatchers.IO).launch {
            cooperativeTask()
            // Note: any built in suspend function like delay() is cooperative!
        }

        job.cancel()
        job.join() // the cooperative task stops working when the coroutine is no longer in "Active" state
    }
}

// This function is cooperative.
private suspend fun cooperativeTask() {
    val start = System.currentTimeMillis()
    val duration = 2000L

    // checks if the Coroutine is in "Active" state
    while (start + duration > System.currentTimeMillis() && coroutineContext.isActive) {
        // do nothing
    }
}

