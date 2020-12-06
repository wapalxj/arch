package com.vero.hilibrary.executor

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.IntRange
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * 支持按任务优先级执行
 * 支持线程池暂停，恢复
 * 可扩展：能力监控等
 */
object HiExecutor {
    private const val TAG = "HiExecutor"
    private var hiExecutor: ThreadPoolExecutor

    //用于暂停
    private var isPaused = false
    private var lock: ReentrantLock = ReentrantLock()
    private var pauseCondition: Condition
    private var mainHandler = Handler(Looper.getMainLooper());


    init {
        pauseCondition = lock.newCondition()
        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cpuCount + 1
        val maxPoolSize = cpuCount * 2 + 1
        val blockingDeque: PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS

        val seq = AtomicLong()
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            thread.name = "hi-executor-" + seq.getAndIncrement()
            return@ThreadFactory thread
        }

        hiExecutor = object : ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingDeque as BlockingQueue<Runnable>,
            threadFactory
        ) {
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                super.beforeExecute(t, r)
                if (isPaused) {
                    lock.lock()
                    try {
                        pauseCondition.await()
                    } finally {
                        lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                super.afterExecute(r, t)
                //可监控线程池耗时任务，线程穿件数量，正在运行的数量
                Log.e(TAG, "HiExecutor执行完了" + (r as? PriorityRunnable)?.priority)
            }
        }

    }

    //普通的执行方法
    @JvmOverloads
    fun execute(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Runnable) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }

    //带回调
    @JvmOverloads
    fun execute(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Callable<*>) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }


    //暂停
    @Synchronized
    fun pause() {
        isPaused = true
        Log.e(TAG, "HiExecutor is paused")
    }

    //resume
    @Synchronized
    fun resume() {
        isPaused = false
        lock.lock()
        try {
            pauseCondition.signalAll()
        } finally {
            lock.unlock()
        }
        Log.e(TAG, "HiExecutor is resumed")
    }

    /**
     * 执行回调
     */
    abstract class Callable<T> : Runnable {
        override fun run() {
            mainHandler.post {
                onPrepare()
            }

            val t: T = onBackground()
            mainHandler.post { onCompleted(t) }

        }

        //执行前
        open fun onPrepare() {

        }

        abstract fun onBackground(): T
        abstract fun onCompleted(t: T)
    }

    //优先级PriorityRunnable
    class PriorityRunnable(val priority: Int, private val runnable: Runnable) : Runnable,
        Comparable<PriorityRunnable> {
        override fun compareTo(other: PriorityRunnable): Int {
            return when {
                this.priority < other.priority -> {
                    1
                }
                this.priority > other.priority -> {
                    -1
                }
                else -> {
                    0
                }
            }
        }

        override fun run() {
            runnable.run()
        }

    }

}