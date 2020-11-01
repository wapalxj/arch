package com.vero.hilibrary.log;

import android.util.Log;

import androidx.annotation.NonNull;

import static com.vero.hilibrary.log.HiLogConfig.MAX_LEN;

/**
 * 控制台打印器
 */
public class HiConsolePrinter implements HiLogPrinter {

    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, String printString) {
        int len = printString.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            //超过一行
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level, tag, printString.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }

            if (index != len) {
                //打印剩余的一行
                Log.println(level, tag, printString.substring(index, len));
            }
        } else {
            //不足一行
            Log.println(level, tag, printString);
        }
    }
}
