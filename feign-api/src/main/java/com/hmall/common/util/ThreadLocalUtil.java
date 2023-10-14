package com.hmall.common.util;


public class ThreadLocalUtil {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setUserId(Long id) {
        threadLocal.set(id);
    }

    public static Long getUserId() {
        return threadLocal.get();
    }

    public static void removeUserId() {
        threadLocal.remove();
    }
}
