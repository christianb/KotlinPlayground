package coroutine

import kotlinx.coroutines.Job

fun threadInfo(): String = Thread.currentThread().name

fun logJobsChildren(job: Job?, prefix: String = "") {
    println("${prefix}job: $job")

    job?.children?.forEach {
        logJobsChildren(it, "$prefix  ")
    }
}

suspend fun benchmark(block: suspend () -> Unit) {
    val start = System.currentTimeMillis()

    block.invoke()

    val end = System.currentTimeMillis()

    println("completed after ${end - start}ms")
}
