package thread

import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.random.Random

/**
 * 生产者-消费者  Condition指定唤醒某个线程工作
 *
 *
 * 生产者： boss 生产砖
 *
 * 砖的序号为偶数，工人2去搬，奇数工人1去搬
 *
 * 消费者：工人1,2
 */

fun main() {
    val task = ReentrantLockDemo3.ReentrantLockTask()
    Thread(Runnable {
        while (true) {
            //工人1搬砖
            task.work1()
        }
    }).start()

    Thread(Runnable {
        while (true) {
            //工人2搬砖
            task.work2()
        }
    }).start()


    for (i in 0 until 10) {
        //生产砖
        task.boss()
    }
}

class ReentrantLockDemo3 {
    internal class ReentrantLockTask {
        val lock = ReentrantLock(true)

        val worker1Condition = lock.newCondition()
        val worker2Condition = lock.newCondition()

        @Volatile
        var flag = 0  //砖的序号

        fun work1() {
            try {
                lock.lock()
                if (flag == 0 || flag % 2 == 0) {
                    println("worker1 无砖可搬")
                    worker1Condition.await()
                }
                println("worker1 搬到的是:$flag")
                flag = 0
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                lock.unlock()
            }
        }

        fun work2() {
            try {
                lock.lock()
                if (flag == 0 || flag % 2 != 0) {
                    println("worker2 无砖可搬")
                    worker2Condition.await()
                }
                println("worker2 搬到的是:$flag")
                flag = 0
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                lock.unlock()
            }
        }

        fun boss() = try {
            lock.lock()
            flag = java.util.Random().nextInt(100)
            if (flag % 2 == 0) {
                worker2Condition.signal()
                println("boss 生产砖，唤醒工人2==$flag")
            } else {
                worker1Condition.signal()
                println("boss 生产砖，唤醒工人1==$flag")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }
}