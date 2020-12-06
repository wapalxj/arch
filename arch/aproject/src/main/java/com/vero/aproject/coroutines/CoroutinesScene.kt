package coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object CoroutinesScene {
    @JvmStatic
    fun main(args: Array<String>) {
        startScene1()
    }

    /**
     * 依次启动3个子线程，并且同步的方式拿到返回值
     */
    fun startScene1() {
        GlobalScope.launch(Dispatchers.Main) {
            println("Coroutine start")
            val res1 = request1()
            val res2 = request2(res1)
            val res3 = request3(res2)
            println("res3==$res3")
            println("work on ${Thread.currentThread().name}")
        }

        println("GlobalScope.launch0000")
    }

    suspend fun request1(): String {
        delay(2 * 1000)//不会暂停线程，但会暂停当前所在协程
        println("request1 work on ${Thread.currentThread().name}")
        return "res111 from request1"
    }

    suspend fun request2(res: String): String {
        delay(2 * 1000)
        println("request2 work on ${Thread.currentThread().name}")
        return "res222 from request2"
    }

    suspend fun request3(res: String): String {
        delay(2 * 1000)
        println("request3 work on ${Thread.currentThread().name}")
        return "res333 from request3"
    }
}