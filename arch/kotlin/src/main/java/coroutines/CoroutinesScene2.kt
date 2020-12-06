package coroutines

import kotlinx.coroutines.*

object CoroutinesScene2 {

    private const val TAG = "CoroutinesScene2"
    suspend fun request2(): String {
        delay(2 * 1000)
        println("request2")
        return "request2"
    }
}