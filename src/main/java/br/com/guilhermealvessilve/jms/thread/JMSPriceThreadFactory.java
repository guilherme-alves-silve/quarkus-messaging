package br.com.guilhermealvessilve.jms.thread;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class JMSPriceThreadFactory implements ThreadFactory {

    private static final AtomicInteger INCREMENT_THREAD_POOL = new AtomicInteger();
    private final AtomicLong INCREMENT = new AtomicLong();

    JMSPriceThreadFactory() {
        INCREMENT_THREAD_POOL.incrementAndGet();
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        return new Thread(runnable, "jms-" + INCREMENT.incrementAndGet() + "-thread-pool-" + INCREMENT_THREAD_POOL.intValue());
    }
}
