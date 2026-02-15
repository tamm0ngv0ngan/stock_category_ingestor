package org.tmvn.stock_category_ingestor.thread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@RequiredArgsConstructor
public class ThreadExecutor {
    private final Semaphore semaphore;
    private final List<? extends Runnable> tasks;

    public ThreadExecutor(int poolSize, List<? extends Runnable> tasks) {
        this.semaphore = new Semaphore(poolSize);
        this.tasks = tasks;
    }

    public void execute() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (Runnable task : tasks) {
                semaphore.acquire();
                executor.submit(() -> {
                    try {
                        task.run();
                    } finally {
                        semaphore.release();
                    }
                });
                Thread.sleep(50);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
