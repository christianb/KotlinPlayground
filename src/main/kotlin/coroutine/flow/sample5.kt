package coroutine.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// Do I need to launch a new Coroutine for collecting a flow?

fun main() = runBlocking {

    val coroutineScope = CoroutineScope(Dispatchers.Default)

    /*
    coroutineScope.launch {
        myFlow().collect {  println(it) }
    }.join()
*/

    myFlow().onEach { println(it) }.launchIn(coroutineScope).join()
}


private fun myFlow(): Flow<Int> = flow {
    for (i in 1..5) {
        delay(100)
        emit(i)
    }
}