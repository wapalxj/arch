package com.vero.hilibrary.log;

public interface HiLogFormatter<T> {
    String format(T data);
}

