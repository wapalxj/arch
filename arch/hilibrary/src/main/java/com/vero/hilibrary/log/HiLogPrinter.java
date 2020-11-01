package com.vero.hilibrary.log;

import android.util.Log;

import androidx.annotation.NonNull;

public interface HiLogPrinter {
    void print(@NonNull HiLogConfig config,int level, String tag, String string);
}
