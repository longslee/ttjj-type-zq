package com.lee.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by longslee on 2019/8/20.
 */
public class NameableThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final String namePrefix;
    private final boolean isDaemon;
    private final AtomicInteger threadId;

    public NameableThreadFactory(String name, boolean isDaemon){
        SecurityManager securityManager = System.getSecurityManager();
        this.group = (securityManager!=null) ? securityManager.getThreadGroup():Thread.currentThread().getThreadGroup();
        this.namePrefix = name;
        this.isDaemon = isDaemon;
        this.threadId = new AtomicInteger(0);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group,r,namePrefix+"-"+threadId.getAndIncrement());
        t.setDaemon(isDaemon);
        if(t.getPriority() != Thread.NORM_PRIORITY){
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

    public void shutdownGracefully(String name){ //TODO

    }
}
