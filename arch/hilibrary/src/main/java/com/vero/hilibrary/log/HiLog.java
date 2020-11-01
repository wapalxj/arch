package com.vero.hilibrary.log;

import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * 1.打印对战信息
 * 2.File输出
 * 3.模拟控制台
 */
public class HiLog {
    private static final String HI_LOG_PACKAGE;

    static {
        String className = HiLog.class.getName();
        HI_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.')+1);
    }

    public static void v(Object... content) {
        log(HiLogType.V, content);
    }

    //带tag的v
    public static void vt(String tag, Object... content) {
        log(HiLogType.V, tag, content);
    }

    public static void d(Object... content) {
        log(HiLogType.D, content);
    }

    public static void dt(String tag, Object... content) {
        log(HiLogType.D, tag, content);
    }

    public static void i(Object... content) {
        log(HiLogType.I, content);
    }

    public static void it(String tag, Object... content) {
        log(HiLogType.I, tag, content);
    }

    public static void w(Object... content) {
        log(HiLogType.W, content);
    }

    public static void wt(String tag, Object... content) {
        log(HiLogType.W, tag, content);
    }

    public static void e(Object... content) {
        log(HiLogType.E, content);
    }

    public static void et(String tag, Object... content) {
        log(HiLogType.E, tag, content);
    }

    public static void a(Object... content) {
        log(HiLogType.A, content);
    }

    public static void at(String tag, Object... content) {
        log(HiLogType.A, tag, content);
    }

    public static void log(@HiLogType.TYPE int type, Object... contents) {
        log(type, HiLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@HiLogType.TYPE int type, String tag, Object... contents) {
        log(HiLogManager.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(@NonNull HiLogConfig config, @HiLogType.TYPE int type, String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (config.includeThread()) {
            //是否包含线程信息
            String threadInfo = HiLogConfig.HI_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            //堆栈信息
            String stackTrace = HiLogConfig.HI_STACK_TRACE_FORMATTER.format(HiStackTraceUtil.getCroppedRealStackTrace(new Throwable().getStackTrace(), HI_LOG_PACKAGE, config.stackTraceDepth()));
            sb.append(stackTrace).append("\n");
        }
        //日志内容
        String body = parseBody(contents, config);
        sb.append(body);
        List<HiLogPrinter> printers = config.printers() != null ? Arrays.asList(config.printers()) : HiLogManager.getInstance().getPrinters();
        if (printers == null) {
            return;
        }
        //遍历打印器进行打印
        for (HiLogPrinter printer : printers) {
            printer.print(config, type, tag, sb.toString());
        }
//        Log.println(type, tag, body);
    }

    private static String parseBody(@NonNull Object[] contents, @NonNull HiLogConfig config) {
        if (config.injectJsonParse() != null) {
            return config.injectJsonParse().toJson(contents);
        }

        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
