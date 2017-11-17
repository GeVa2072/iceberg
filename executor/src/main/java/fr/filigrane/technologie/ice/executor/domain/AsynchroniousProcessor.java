package fr.filigrane.technologie.ice.executor.domain;

import fr.filigrane.technologie.ice.executor.domain.scenario.ScenarioProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AsynchroniousProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsynchroniousProcessor.class);

    private final ExecutorService executorService;
    private final ThreadGroup group;
    private final AtomicInteger groupNumber = new AtomicInteger(1);
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public AsynchroniousProcessor() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" + getClass().getSimpleName() + "-" + groupNumber + "-thread-";
        executorService = new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue(), (r) -> new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0));

        LOGGER.info("{} with thread pool created : {}", getClass().getSimpleName(), namePrefix);
    }

    protected void submit(Runnable runnable) {
        executorService.submit(runnable);
    }
}
