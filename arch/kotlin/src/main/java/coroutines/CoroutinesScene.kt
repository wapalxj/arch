package coroutines

import kotlinx.coroutines.*

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
            //此处运行的线程，即为上面方法参数指定的
            println("Coroutine start")
            val res1 = request1()
            val res2 = request2(res1)
            val res3 = request3(res2)
            println("res3==$res3")
            println("work on ${Thread.currentThread().name}")
        }

        println("GlobalScope.launch0000")
    }

    /**
     * 启动一个线程，先执行req1，完了之后，同时运行req2和req3,这俩并发完成后再向下执行
     */
    fun startScene2() {
        GlobalScope.launch(Dispatchers.Main) {
            //此处运行的线程，即为上面方法参数指定的
            println("Coroutine start2222")
            val res1 = request1()
//            val res2 = request2(res1)
//            val res3 = request3(res2)
//            println("res3==$res3")
            val deferred2 = GlobalScope.async { request2(res1) }
            val deferred3 = GlobalScope.async { request3(res1) }

            //这里await不能上下调用,错误：
//            deferred2.await()
//            deferred3.await()
            updateUI(deferred2.await(), deferred3.await())

        }

        println("GlobalScope.launch2222")
    }

    private fun updateUI(await: String, await1: String) {
        println("work on ${Thread.currentThread().name}")
    }


    //delay既然是IO异步任务，是如何做到延迟协程中的代码向下执行的？
    suspend fun request1(): String {
        //delay():异步操作，在子线程执行
        //不会暂停线程，但会暂停当前所在协程
        delay(2 * 1000)

        //回到 GlobalScope.launch()指定的线程
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