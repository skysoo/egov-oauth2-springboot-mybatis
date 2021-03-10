package com.study.egovspringbootmybatis.utility;

import java.util.concurrent.*;

/**
 * @author skysoo
 * @version 1.0.0
 * @since 2020-05-06 오후 4:33
 **/
public class ScheduleUtil {
    private static final SchedulerThreadFactory FACTORY = new SchedulerThreadFactory();
    private static final ScheduleUtil o = new ScheduleUtil();

    private ScheduleUtil() {
    }

    public static ScheduleUtil get() {
        return o;
    }
    /* 시작 딜레이 이후 첫번째 실행을 시작으로 지정한 시간만큼 차이로 반복 실챙 */
    public ScheduledExecutorService pool(Runnable fuc, int delay, TimeUnit unit, int poolCount) {
        ScheduledExecutorService schedulerPool = this._createPool(poolCount);
        schedulerPool.scheduleAtFixedRate(fuc, 0L, (long)delay, unit);
        return schedulerPool;
    }
    /* 시작딜레이 이후 첫번째 실행을 시작으로 해당 커맨드의 동작이 종료된 이후 다음 실행시간까지 지정한 시간만큼 딜레이를 가지면서 반복 실행 */
    public ScheduledExecutorService poolAfterDone(Runnable fuc, int delay, TimeUnit unit, int poolCount) {
        ScheduledExecutorService schedulerPool = Executors.newScheduledThreadPool(poolCount, new SchedulerThreadFactory());
        schedulerPool.scheduleWithFixedDelay(fuc, 0L, (long)delay, unit);
        return schedulerPool;
    }

    public ScheduledExecutorService poolPerSec(Runnable fuc, int delay, int poolCount) {
        return this.pool(fuc, delay, TimeUnit.SECONDS, poolCount);
    }

    public ScheduledExecutorService poolPerSecAfterDone(Runnable fuc, int delay, int poolCount) {
        return this.poolAfterDone(fuc, delay, TimeUnit.SECONDS, poolCount);
    }

    public ScheduledFuture<?> single(Runnable func, int initDelay, int delay, TimeUnit unit) {
        ScheduledExecutorService pool = this._createPool(1);
        return pool.scheduleAtFixedRate(func, (long)initDelay, (long)delay, unit);
    }

    public ScheduledFuture<?> single(Runnable func, int delay, TimeUnit unit) {
        return this.single(func, delay, delay, unit);
    }

    public ScheduledFuture<?> singleAfterDone(Runnable func, int initDelay, int delay, TimeUnit unit) {
        ScheduledExecutorService pool = this._createPool(1);
        return pool.scheduleWithFixedDelay(func, (long)initDelay, (long)delay, unit);
    }

    public ScheduledFuture<?> singleAfterDone(Runnable func, int delay, TimeUnit unit) {
        return this.singleAfterDone(func, delay, delay, unit);
    }

    public ScheduledFuture<?> singlePerSec(Runnable func, int delay) {
        return this.single(func, delay, TimeUnit.SECONDS);
    }

    public ScheduledFuture<?> singlePerSecAfterDone(Runnable func, int delay) {
        return this.singleAfterDone(func, delay, TimeUnit.SECONDS);
    }

    private ScheduledExecutorService _createPool(int poolCount) {
        return Executors.newScheduledThreadPool(poolCount, FACTORY);
    }

    private static class SchedulerThreadFactory implements ThreadFactory {
        private final ThreadGroup group;

        SchedulerThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            this.group = s != null ? s.getThreadGroup() : new ThreadGroup("SCH-GRP");
        }

        public Thread newThread(Runnable r) {
            Thread.setDefaultUncaughtExceptionHandler(new ThreadHandler("DefaultHandler-"));
            Thread t = new Thread(this.group, r, "SCH-" + r.getClass().getSimpleName().toUpperCase(), 0L);

            if (t.isDaemon()) {
                t.setDaemon(false);
            }

            if (t.getPriority() != 5) {
                t.setPriority(5);
            }

            return t;
        }
    }
}
