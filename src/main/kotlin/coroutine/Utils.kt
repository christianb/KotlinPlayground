package coroutine

import kotlinx.coroutines.Job

fun threadInfo(): String = Thread.currentThread().name

fun logThreadInfo() {
    println("Thread: ${threadInfo()}")
}

fun logJobsChildren(job: Job?, prefix: String = "") {
    println("${prefix}job: $job")

    job?.children?.forEach {
        logJobsChildren(it, "$prefix  ")
    }
}
