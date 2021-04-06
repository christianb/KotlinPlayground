package coroutine.scope

import coroutine.benchmark
import kotlinx.coroutines.*

// How to pass CoroutineScope to function?

fun main() = runBlocking {
    benchmark {
        println("concurrentSum = ${concurrentSum1()}")
        println("concurrentSum = ${concurrentSum2()}")
        println("concurrentSum = ${concurrentSum3()}")
    }
}

// Either by using coroutineScope lambda
// But this let all children jobs fail if one fails!
private suspend fun concurrentSum1(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

// Or by using supervisorScope lambda
// This let other children jobs run if one child fails.
private suspend fun CoroutineScope.concurrentSum2(): Int = supervisorScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

// Or by extension function
// What is the behavior when one child fails?
private suspend fun CoroutineScope.concurrentSum3(): Int {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    return one.await() + two.await()
}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}