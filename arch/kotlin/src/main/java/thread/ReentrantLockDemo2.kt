package thread

import java.util.concurrent.locks.ReentrantLock

/**
 * 公平锁/非公平锁
 */

fun main() {
    val task = ReentrantLockDemo2.ReentrantLockTask()
    val runnable = Runnable {
        task.print()
    }
    for (i in 0 until 10) {
        Thread(runnable).start()
    }
}

class ReentrantLockDemo2 {
    internal class ReentrantLockTask {
        val lock = ReentrantLock(true)

        fun print() {
            val name = Thread.currentThread().name
            try {
                lock.lock()
                //打印2次
                println("$name 第1次打印")
                Thread.sleep(1000)
                lock.unlock()

                //再次申请锁
                //公平锁:所有线程会全部先打印第1次
                //然后继续依次打印第2次

                lock.lock()
                println("$name 第2次打印")

            } finally {
                lock.unlock()
            }
        }
    }
}