package thread

import java.lang.Exception
import java.util.concurrent.locks.ReentrantReadWriteLock


fun main() {
    val task = ReentrantReadWriteLocDemo()

    for (i in 0 until 3) {
        Thread(Runnable {
            //3个线程读
            task.read()
        }).start()
    }

    for (i in 0 until 3) {
        Thread(Runnable {
            //3个线程写
            task.write()
        }).start()
    }
}

class ReentrantReadWriteLocDemo {
    private val lock = ReentrantReadWriteLock()

    //安全的read
    private val readLock = lock.readLock()

    //安全的write
    private val writeLock = lock.writeLock()


    fun read() {
        val name = Thread.currentThread().name
        try {
            readLock.lock()
            println("线程$name 正在读取数据...")
            Thread.sleep(1000)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            readLock.unlock()
            println("线程$name 释放读锁...")
        }
    }

    fun write() {
        val name = Thread.currentThread().name
        try {
            writeLock.lock()
            println("线程$name 正在读写入数据...")
            Thread.sleep(1000)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            writeLock.unlock()
            println("线程$name 释放写锁...")
        }
    }
}