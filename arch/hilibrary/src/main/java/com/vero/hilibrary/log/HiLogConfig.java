package com.vero.hilibrary.log;

public abstract class HiLogConfig {

    static int MAX_LEN = 512;//每一行最大字符数

    //懒汉式单例
    static HiStackTraceFormatter HI_STACK_TRACE_FORMATTER = new HiStackTraceFormatter();
    static HiThreadFormatter HI_THREAD_FORMATTER = new HiThreadFormatter();

    //默认的tag
    public String getGlobalTag() {
        return "HiLog";
    }

    public boolean enable() {
        return true;
    }

    public JsonParser injectJsonParse() {
        return null;
    }

    //日志是否包含线程信息
    public boolean includeThread() {
        return false;
    }

    //堆栈深度
    public int stackTraceDepth() {
        return 5;
    }

    public HiLogPrinter[] printers() {
        return null;
    }

    //抽象json解析器，不依赖于GSON等某个确定的库
    public interface JsonParser {
        String toJson(Object src);
    }

}
