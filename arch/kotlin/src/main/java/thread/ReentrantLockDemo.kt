package thread

import java.util.concurrent.locks.ReentrantLock


fun main() {
    val task = ReentrantLockDemo.ReentrantLockTask()
    val runnable = Runnable {
        task.buyTicket()
    }

    for (i in 0 until 10) {
        Thread(runnable).start()
    }

}


class ReentrantLockDemo {
    internal class ReentrantLockTask {
        private val mReentrantLock = ReentrantLock()

        fun buyTicket() {
            val name = Thread.currentThread().name
            try {
                mReentrantLock.lock()
                println("$name 准备好了")
                Thread.sleep(100)
                println("$name 买好了")

                mReentrantLock.lock()
                println("$name 又准备好了")
                Thread.sleep(100)
                println("$name 又买好了")

                mReentrantLock.lock()
                println("$name 又准备好了")
                Thread.sleep(100)
                println("$name 又买好了---end")
            } finally {
                //可重入锁，获取3次，就要释放3次
                mReentrantLock.unlock()
                mReentrantLock.unlock()
                mReentrantLock.unlock()
            }
        }
    }
}