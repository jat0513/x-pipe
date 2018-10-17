package com.ctrip.xpipe.redis.console.healthcheck.crossdc;

import com.ctrip.xpipe.concurrent.AbstractExceptionLogTask;
import com.ctrip.xpipe.redis.console.healthcheck.AbstractHealthCheckAction;
import com.ctrip.xpipe.redis.console.healthcheck.RedisHealthCheckInstance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author chen.zhu
 * <p>
 * Oct 06, 2018
 */
public abstract class AbstractCDLAHealthCheckAction extends AbstractHealthCheckAction implements CrossDcLeaderAwareHealthCheckAction {

    public AbstractCDLAHealthCheckAction(ScheduledExecutorService scheduled, RedisHealthCheckInstance instance, ExecutorService executors) {
        super(scheduled, instance, executors);
    }

    @Override
    protected int getBaseCheckInterval() {
        return getActionInstance().getHealthCheckConfig().getRedisConfCheckIntervalMilli();
    }


    protected abstract class AsyncRun {

        public void run() {
            executors.execute(new AbstractExceptionLogTask() {
                @Override
                protected void doRun() throws Exception {
                    doRun0();
                }
            });
        }

        protected abstract void doRun0();
    }
}
