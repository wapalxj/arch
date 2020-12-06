package coroutines

import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import kotlin.coroutines.Continuation

object CoroutinesScene3 {
    private const val TAG = "CoroutinesScene3"

    fun startScene3() {
        val callback = Continuation<String>(Dispatchers.Main) {
             System.out.println("CoroutinesScene3")
        }

        CoroutinesScene2_decompiled.request1(callback)
    }


    /**
     * 演示以异步的方式，适配协程
     */
//    suspend fun parseAssetsFile(assetManager: AssetManager, fileName: String): String {
//        return suspendCancellableCoroutine { continuation ->
//            Thread(Runnable {
//                val inputStream = assetManager.open(fileName)
//                val br = BufferedReader(InputStreamReader(inputStream))
//                var line: String? = null
//                val stringBuilder = StringBuilder()
//
//                do {
//                    line = br.readLine()
//                    if (line != null) {
//                        stringBuilder.append(line)
//                    } else {
//                        break
//                    }
//                } while (true)
//
//                inputStream.close()
//                br.close()
//
//                Thread.sleep(2000)
//
//                Log.e("CoroutinesScene3", "parseAssetsFile")
//                continuation.resumeWith(Result.success(stringBuilder.toString()))
//            }).start()
//
//        }
//    }
}