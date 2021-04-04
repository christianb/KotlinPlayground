package coroutine

import kotlinx.coroutines.Job

fun threadInfo(): String = Thread.currentThread().name

fun logThreadInfo() {
    println("Thread: ${threadInfo()}")
}

fun logJobsChildren(job: Job?) {
    job?.children?.forEach {
        println("child job: $it")
    }
}