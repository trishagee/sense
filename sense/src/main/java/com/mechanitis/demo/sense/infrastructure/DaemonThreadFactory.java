package com.mechanitis.demo.sense.infrastructure;

import java.util.concurrent.ThreadFactory;

public final class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(final Runnable runnable) {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    }
}